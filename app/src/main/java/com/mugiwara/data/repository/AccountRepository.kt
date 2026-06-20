package com.mugiwara.data.repository

import com.mugiwara.data.local.AccountDao
import com.mugiwara.data.model.AccountEntity
import com.mugiwara.data.remote.MT5ApiService
import com.mugiwara.data.remote.dto.LoginRequest
import com.mugiwara.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val apiService: MT5ApiService
) {
    fun getAllAccounts(): Flow<List<AccountEntity>> = accountDao.getAllAccounts()
    
    fun getActiveAccount(): Flow<AccountEntity?> = flow {
        emit(accountDao.getActiveAccount())
    }
    
    suspend fun getAccountById(id: String): AccountEntity? = accountDao.getAccountById(id)
    
    suspend fun addAccount(account: AccountEntity) = accountDao.insertAccount(account)
    
    suspend fun updateAccount(account: AccountEntity) = accountDao.updateAccount(account)
    
    suspend fun deleteAccount(id: String) = accountDao.deleteAccountById(id)
    
    suspend fun setActiveAccount(id: String) {
        accountDao.deactivateAllAccounts()
        accountDao.activateAccount(id)
    }
    
    suspend fun login(server: String, login: String, password: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(server, login, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.token)
                } else {
                    Result.Error(Exception("Empty response body"))
                }
            } else {
                Result.Error(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    suspend fun getAccountCount(): Int = accountDao.getAccountCount()
}
