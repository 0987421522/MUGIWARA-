package com.mugiwara.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mugiwara.data.local.entity.SymbolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SymbolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymbol(symbol: SymbolEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(symbols: List<SymbolEntity>)

    @Query("SELECT * FROM symbols")
    fun getAllSymbolsFlow(): Flow<List<SymbolEntity>>

    @Query("SELECT * FROM symbols")
    suspend fun getAllSymbols(): List<SymbolEntity>

    // تم استخدام symbol_group بدلاً من group لتجنب خطأ SQL المحجوز
    @Query("SELECT * FROM symbols WHERE symbol_group = :groupName")
    fun getSymbolsByGroup(groupName: String): Flow<List<SymbolEntity>>

    @Query("SELECT * FROM symbols WHERE symbol = :symbol")
    suspend fun getSymbolBySymbol(symbol: String): SymbolEntity?

    @Query("DELETE FROM symbols")
    suspend fun deleteAll()
}
