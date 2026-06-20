package com.mugiwara.service.trading

import com.mugiwara.data.model.AccountEntity
import com.mugiwara.data.model.SignalEntity
import com.mugiwara.data.model.TradeEntity
import com.mugiwara.data.remote.dto.OpenTradeRequest
import com.mugiwara.data.repository.AccountRepository
import com.mugiwara.data.repository.TradeRepository
import com.mugiwara.domain.model.Settings
import com.mugiwara.utils.Result
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TradeExecutionEngine @Inject constructor(
    private val tradeRepository: TradeRepository,
    private val accountRepository: AccountRepository,
    private val riskManager: RiskManager,
    private val moneyManager: MoneyManager
) {
    
    suspend fun executeSignal(
        signal: SignalEntity,
        account: AccountEntity,
        settings: Settings,
        token: String
    ): Result<TradeEntity> {
        try {
            // Validate risk
            val dailyTradeCount = tradeRepository.getActiveTradeCount()
            val dailyLoss = tradeRepository.getDailyProfit(
                java.util.Calendar.getInstance().apply {
                    set(java.util.Calendar.HOUR_OF_DAY, 0)
                    set(java.util.Calendar.MINUTE, 0)
                    set(java.util.Calendar.SECOND, 0)
                }.timeInMillis
            )
            
            val trade = createTradeFromSignal(signal, account, settings)
            
            val validation = riskManager.validateTrade(
                account = account,
                trade = trade,
                settings = settings,
                dailyTradeCount = dailyTradeCount,
                dailyLoss = dailyLoss
            )
            
            if (validation is RiskManager.RiskValidation.Failed) {
                return Result.Error(Exception("Risk validation failed: ${validation.failedChecks.joinToString { it.message }}"))
            }
            
            // Execute via API
            val request = OpenTradeRequest(
                symbol = signal.symbol,
                type = signal.direction,
                volume = trade.lotSize,
                price = signal.entryPrice,
                sl = trade.stopLoss,
                tp = trade.takeProfit,
                comment = "MUGIWARA Auto",
                magic = 123456
            )
            
            val result = tradeRepository.openTrade(token, request)
            
            if (result is Result.Success) {
                tradeRepository.addTrade(trade)
                return Result.Success(trade)
            } else {
                return Result.Error(Exception("Failed to execute trade"))
            }
            
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
    
    suspend fun closeTrade(
        tradeId: String,
        currentPrice: Double,
        token: String
    ): Result<Unit> {
        try {
            val trade = tradeRepository.getTradeById(tradeId) ?: return Result.Error(Exception("Trade not found"))
            
            val request = com.mugiwara.data.remote.dto.CloseTradeRequest(
                ticket = tradeId,
                volume = trade.lotSize,
                price = currentPrice
            )
            
            return tradeRepository.closeTrade(token, request)
            
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
    
    suspend fun modifyTrade(
        tradeId: String,
        newStopLoss: Double?,
        newTakeProfit: Double?,
        token: String
    ): Result<Unit> {
        try {
            val request = com.mugiwara.data.remote.dto.ModifyTradeRequest(
                ticket = tradeId,
                sl = newStopLoss,
                tp = newTakeProfit
            )
            
            return tradeRepository.modifyTrade(token, request)
            
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
    
    suspend fun updateTrailingStops(
        activeTrades: List<TradeEntity>,
        currentPrices: Map<String, Double>,
        settings: Settings,
        token: String
    ) {
        for (trade in activeTrades) {
            if (!settings.trailingStopEnabled) continue
            
            val currentPrice = currentPrices[trade.symbol] ?: continue
            
            val newStopLoss = riskManager.calculateTrailingStop(
                currentPrice = currentPrice,
                entryPrice = trade.entryPrice,
                initialStopLoss = trade.stopLoss,
                trailingPips = settings.trailingStopPips.toDouble(),
                direction = trade.type
            )
            
            if (newStopLoss != trade.stopLoss) {
                modifyTrade(trade.id, newStopLoss, trade.takeProfit, token)
            }
        }
    }
    
    private fun createTradeFromSignal(
        signal: SignalEntity,
        account: AccountEntity,
        settings: Settings
    ): TradeEntity {
        val lotSize = riskManager.calculateLotSize(
            accountBalance = account.balance,
            riskPercent = settings.defaultRiskPercent,
            stopLossPips = kotlin.math.abs(signal.entryPrice - signal.stopLoss),
            pipValue = 10.0,
            symbol = signal.symbol
        )
        
        return TradeEntity(
            id = UUID.randomUUID().toString(),
            accountId = account.id,
            symbol = signal.symbol,
            type = signal.direction,
            entryPrice = signal.entryPrice,
            exitPrice = null,
            currentPrice = signal.entryPrice,
            lotSize = lotSize,
            stopLoss = signal.stopLoss,
            takeProfit = signal.takeProfit,
            trailingStop = if (settings.trailingStopEnabled) signal.stopLoss else null,
            commission = 0.0,
            swap = 0.0,
            profit = 0.0,
            status = "ACTIVE",
            openTime = System.currentTimeMillis(),
            closeTime = null,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    }
}
