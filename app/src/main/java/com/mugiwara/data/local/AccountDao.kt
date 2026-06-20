package com.mugiwara.data.local

import androidx.room.*
import com.mugiwara.data.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts ORDER BY createdAt DESC")
    fun getAllAccounts(): Flow<List<AccountEntity>>
    
    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: String): AccountEntity?
    
    @Query("SELECT * FROM accounts WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveAccount(): AccountEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountEntity>)
    
    @Update
    suspend fun updateAccount(account: AccountEntity)
    
    @Delete
    suspend fun deleteAccount(account: AccountEntity)
    
    @Query("DELETE FROM accounts WHERE id = :id")
    suspend fun deleteAccountById(id: String)
    
    @Query("UPDATE accounts SET isActive = 0")
    suspend fun deactivateAllAccounts()
    
    @Query("UPDATE accounts SET isActive = 1 WHERE id = :id")
    suspend fun activateAccount(id: String)
    
    @Query("SELECT COUNT(*) FROM accounts")
    suspend fun getAccountCount(): Int
}
