package com.mugiwara.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for User Account operations
 */
@Dao
interface UserAccountDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userAccount: com.mugiwara.data.local.entity.UserAccountEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userAccounts: List<com.mugiwara.data.local.entity.UserAccountEntity>)
    
    @Update
    suspend fun update(userAccount: com.mugiwara.data.local.entity.UserAccountEntity)
    
    @Delete
    suspend fun delete(userAccount: com.mugiwara.data.local.entity.UserAccountEntity)
    
    @Query("SELECT * FROM user_accounts WHERE user_id = :userId")
    suspend fun getUserById(userId: String): com.mugiwara.data.local.entity.UserAccountEntity?
    
    @Query("SELECT * FROM user_accounts WHERE username = :username")
    suspend fun getUserByUsername(username: String): com.mugiwara.data.local.entity.UserAccountEntity?
    
    @Query("SELECT * FROM user_accounts WHERE email = :email")
    suspend fun getUserByEmail(email: String): com.mugiwara.data.local.entity.UserAccountEntity?
    
    @Query("SELECT * FROM user_accounts")
    fun getAllUsers(): Flow<List<com.mugiwara.data.local.entity.UserAccountEntity>>
    
    @Query("SELECT * FROM user_accounts WHERE is_verified = 1")
    fun getVerifiedUsers(): Flow<List<com.mugiwara.data.local.entity.UserAccountEntity>>
    
    @Query("UPDATE user_accounts SET last_login = :timestamp WHERE user_id = :userId")
    suspend fun updateLastLogin(userId: String, timestamp: Long)
    
    @Query("DELETE FROM user_accounts WHERE user_id = :userId")
    suspend fun deleteUserById(userId: String)
}

/**
 * DAO for Trading Account operations
 */
@Dao
interface TradingAccountDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: com.mugiwara.data.local.entity.TradingAccountEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accounts: List<com.mugiwara.data.local.entity.TradingAccountEntity>)
    
    @Update
    suspend fun update(account: com.mugiwara.data.local.entity.TradingAccountEntity)
    
    @Delete
    suspend fun delete(account: com.mugiwara.data.local.entity.TradingAccountEntity)
    
    @Query("SELECT * FROM trading_accounts WHERE account_id = :accountId")
    suspend fun getAccountById(accountId: String): com.mugiwara.data.local.entity.TradingAccountEntity?
    
    @Query("SELECT * FROM trading_accounts WHERE user_id = :userId")
    fun getUserAccounts(userId: String): Flow<List<com.mugiwara.data.local.entity.TradingAccountEntity>>
    
    @Query("SELECT * FROM trading_accounts WHERE is_connected = 1")
    fun getConnectedAccounts(): Flow<List<com.mugiwara.data.local.entity.TradingAccountEntity>>
    
    @Query("SELECT * FROM trading_accounts WHERE account_type = :type")
    fun getAccountsByType(type: String): Flow<List<com.mugiwara.data.local.entity.TradingAccountEntity>>
    
    @Query("SELECT * FROM trading_accounts ORDER BY balance DESC LIMIT 1")
    suspend fun getAccountWithLargestBalance(): com.mugiwara.data.local.entity.TradingAccountEntity?
    
    @Query("UPDATE trading_accounts SET balance = :balance, equity = :equity, free_margin = :freeMargin, margin_level = :marginLevel, last_sync = :timestamp WHERE account_id = :accountId")
    suspend fun updateAccountBalance(
        accountId: String,
        balance: Double,
        equity: Double,
        freeMargin: Double,
        marginLevel: Double,
        timestamp: Long
    )
    
    @Query("UPDATE trading_accounts SET is_connected = :isConnected WHERE account_id = :accountId")
    suspend fun updateConnectionStatus(accountId: String, isConnected: Boolean)
    
    @Query("DELETE FROM trading_accounts WHERE account_id = :accountId")
    suspend fun deleteAccountById(accountId: String)
    
    @Query("SELECT COUNT(*) FROM trading_accounts")
    suspend fun getAccountCount(): Int
}

/**
 * DAO for Order operations
 */
@Dao
interface OrderDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: com.mugiwara.data.local.entity.OrderEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<com.mugiwara.data.local.entity.OrderEntity>)
    
    @Update
    suspend fun update(order: com.mugiwara.data.local.entity.OrderEntity)
    
    @Delete
    suspend fun delete(order: com.mugiwara.data.local.entity.OrderEntity)
    
    @Query("SELECT * FROM orders WHERE order_id = :orderId")
    suspend fun getOrderById(orderId: String): com.mugiwara.data.local.entity.OrderEntity?
    
    @Query("SELECT * FROM orders WHERE account_id = :accountId")
    fun getAccountOrders(accountId: String): Flow<List<com.mugiwara.data.local.entity.OrderEntity>>
    
    @Query("SELECT * FROM orders WHERE account_id = :accountId AND status = :status")
    fun getOrdersByStatus(accountId: String, status: String): Flow<List<com.mugiwara.data.local.entity.OrderEntity>>
    
    @Query("SELECT * FROM orders WHERE account_id = :accountId AND status = 'PENDING'")
    fun getPendingOrders(accountId: String): Flow<List<com.mugiwara.data.local.entity.OrderEntity>>
    
    @Query("SELECT * FROM orders WHERE symbol = :symbol AND status = 'ACTIVE'")
    suspend fun getActiveOrdersBySymbol(symbol: String): List<com.mugiwara.data.local.entity.OrderEntity>
    
    @Query("SELECT * FROM orders WHERE account_id = :accountId ORDER BY created_at DESC LIMIT :limit")
    suspend fun getRecentOrders(accountId: String, limit: Int = 50): List<com.mugiwara.data.local.entity.OrderEntity>
    
    @Query("UPDATE orders SET status = :status, executed_at = :timestamp WHERE order_id = :orderId")
    suspend fun updateOrderStatus(orderId: String, status: String, timestamp: Long)
    
    @Query("SELECT COUNT(*) FROM orders WHERE account_id = :accountId AND status = 'ACTIVE'")
    suspend fun getActiveOrderCount(accountId: String): Int
}

/**
 * DAO for Position operations
 */
@Dao
interface PositionDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(position: com.mugiwara.data.local.entity.PositionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(positions: List<com.mugiwara.data.local.entity.PositionEntity>)
    
    @Update
    suspend fun update(position: com.mugiwara.data.local.entity.PositionEntity)
    
    @Delete
    suspend fun delete(position: com.mugiwara.data.local.entity.PositionEntity)
    
    @Query("SELECT * FROM positions WHERE position_id = :positionId")
    suspend fun getPositionById(positionId: String): com.mugiwara.data.local.entity.PositionEntity?
    
    @Query("SELECT * FROM positions WHERE account_id = :accountId")
    fun getAccountPositions(accountId: String): Flow<List<com.mugiwara.data.local.entity.PositionEntity>>
    
    @Query("SELECT * FROM positions WHERE symbol = :symbol")
    fun getPositionsBySymbol(symbol: String): Flow<List<com.mugiwara.data.local.entity.PositionEntity>>
    
    @Query("SELECT * FROM positions WHERE account_id = :accountId AND type = :type")
    fun getPositionsByType(accountId: String, type: String): Flow<List<com.mugiwara.data.local.entity.PositionEntity>>
    
    @Query("SELECT COUNT(*) FROM positions WHERE account_id = :accountId")
    suspend fun getOpenPositionCount(accountId: String): Int
    
    @Query("SELECT SUM(profit_loss) FROM positions WHERE account_id = :accountId")
    suspend fun getTotalOpenProfit(accountId: String): Double?
    
    @Query("SELECT SUM(volume) FROM positions WHERE account_id = :accountId")
    suspend fun getTotalOpenVolume(accountId: String): Double?
    
    @Query("UPDATE positions SET current_price = :price, profit_loss = :profitLoss, profit_loss_percent = :profitPercent, updated_at = :timestamp WHERE position_id = :positionId")
    suspend fun updatePositionPrice(
        positionId: String,
        price: Double,
        profitLoss: Double,
        profitPercent: Double,
        timestamp: Long
    )
    
    @Query("DELETE FROM positions WHERE position_id = :positionId")
    suspend fun deletePositionById(positionId: String)
}

/**
 * DAO for Deal operations
 */
@Dao
interface DealDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deal: com.mugiwara.data.local.entity.DealEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(deals: List<com.mugiwara.data.local.entity.DealEntity>)
    
    @Update
    suspend fun update(deal: com.mugiwara.data.local.entity.DealEntity)
    
    @Delete
    suspend fun delete(deal: com.mugiwara.data.local.entity.DealEntity)
    
    @Query("SELECT * FROM deals WHERE deal_id = :dealId")
    suspend fun getDealById(dealId: String): com.mugiwara.data.local.entity.DealEntity?
    
    @Query("SELECT * FROM deals WHERE account_id = :accountId")
    fun getAccountDeals(accountId: String): Flow<List<com.mugiwara.data.local.entity.DealEntity>>
    
    @Query("SELECT * FROM deals WHERE account_id = :accountId ORDER BY close_time DESC LIMIT :limit")
    suspend fun getRecentDeals(accountId: String, limit: Int = 100): List<com.mugiwara.data.local.entity.DealEntity>
    
    @Query("SELECT * FROM deals WHERE symbol = :symbol AND account_id = :accountId")
    fun getDealsBySymbol(symbol: String, accountId: String): Flow<List<com.mugiwara.data.local.entity.DealEntity>>
    
    @Query("SELECT * FROM deals WHERE type = :type AND account_id = :accountId")
    fun getDealsByType(type: String, accountId: String): Flow<List<com.mugiwara.data.local.entity.DealEntity>>
    
    @Query("SELECT COUNT(*) FROM deals WHERE account_id = :accountId")
    suspend fun getTotalDealCount(accountId: String): Int
    
    @Query("SELECT COUNT(*) FROM deals WHERE account_id = :accountId AND profit_loss > 0")
    suspend fun getWinningTradeCount(accountId: String): Int
    
    @Query("SELECT COUNT(*) FROM deals WHERE account_id = :accountId AND profit_loss < 0")
    suspend fun getLosingTradeCount(accountId: String): Int
    
    @Query("SELECT SUM(profit_loss) FROM deals WHERE account_id = :accountId")
    suspend fun getTotalProfit(accountId: String): Double?
    
    @Query("SELECT AVG(duration_seconds) FROM deals WHERE account_id = :accountId")
    suspend fun getAverageTradeDuration(accountId: String): Double?
    
    @Query("SELECT * FROM deals WHERE account_id = :accountId AND close_time >= :startTime AND close_time <= :endTime")
    suspend fun getDealsByDateRange(
        accountId: String,
        startTime: Long,
        endTime: Long
    ): List<com.mugiwara.data.local.entity.DealEntity>
}
