package com.mugiwara.data.repository

import com.mugiwara.data.local.SignalDao
import com.mugiwara.data.model.SignalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalRepository @Inject constructor(
    private val signalDao: SignalDao
) {
    fun getAllSignals(): Flow<List<SignalEntity>> = signalDao.getAllSignals()
    
    fun getPendingSignals(): Flow<List<SignalEntity>> = signalDao.getPendingSignals()
    
    fun getSignalsBySymbol(symbol: String): Flow<List<SignalEntity>> = 
        signalDao.getSignalsBySymbol(symbol)
    
    suspend fun getSignalById(id: String): SignalEntity? = signalDao.getSignalById(id)
    
    suspend fun addSignal(signal: SignalEntity) = signalDao.insertSignal(signal)
    
    suspend fun addSignals(signals: List<SignalEntity>) = signalDao.insertSignals(signals)
    
    suspend fun updateSignal(signal: SignalEntity) = signalDao.updateSignal(signal)
    
    suspend fun markSignalExecuted(id: String) = signalDao.markSignalExecuted(id)
    
    suspend fun filterSignal(id: String, reason: String) = signalDao.filterSignal(id, reason)
    
    suspend fun deleteSignal(signal: SignalEntity) = signalDao.deleteSignal(signal)
    
    suspend fun deleteOldSignals(timestamp: Long) = signalDao.deleteOldSignals(timestamp)
    
    suspend fun getPendingSignalCount(): Int = signalDao.getPendingSignalCount()
}
