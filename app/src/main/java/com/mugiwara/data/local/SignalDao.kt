package com.mugiwara.data.local

import androidx.room.*
import com.mugiwara.data.model.SignalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SignalDao {
    @Query("SELECT * FROM signals ORDER BY createdAt DESC")
    fun getAllSignals(): Flow<List<SignalEntity>>
    
    @Query("SELECT * FROM signals WHERE isExecuted = 0 AND isFiltered = 0 ORDER BY confidence DESC")
    fun getPendingSignals(): Flow<List<SignalEntity>>
    
    @Query("SELECT * FROM signals WHERE symbol = :symbol ORDER BY createdAt DESC")
    fun getSignalsBySymbol(symbol: String): Flow<List<SignalEntity>>
    
    @Query("SELECT * FROM signals WHERE id = :id")
    suspend fun getSignalById(id: String): SignalEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSignal(signal: SignalEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSignals(signals: List<SignalEntity>)
    
    @Update
    suspend fun updateSignal(signal: SignalEntity)
    
    @Query("UPDATE signals SET isExecuted = 1 WHERE id = :id")
    suspend fun markSignalExecuted(id: String)
    
    @Query("UPDATE signals SET isFiltered = 1, reason = :reason WHERE id = :id")
    suspend fun filterSignal(id: String, reason: String)
    
    @Delete
    suspend fun deleteSignal(signal: SignalEntity)
    
    @Query("DELETE FROM signals WHERE createdAt < :timestamp")
    suspend fun deleteOldSignals(timestamp: Long)
    
    @Query("SELECT COUNT(*) FROM signals WHERE isExecuted = 0 AND isFiltered = 0")
    suspend fun getPendingSignalCount(): Int
}
