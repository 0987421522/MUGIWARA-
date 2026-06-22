package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.data.mapper.AccountMapper
import com.mugiwara.data.repository.AccountRepository
import com.mugiwara.domain.model.Account
import com.mugiwara.utils.Result
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _loginState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val loginState: StateFlow<UiState<String>> = _loginState.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            accountRepository.getAllAccounts().collect { entities ->
                _accounts.value = entities.map { AccountMapper.toDomain(it) }
            }
        }
    }

    fun login(server: String, login: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            val result = accountRepository.login(server, login, password)
            when (result) {
                is Result.Success -> {
                    _loginState.value = UiState.Success(result.data)
                }
                is Result.Error -> {
                    _loginState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun addAccount(account: Account) {
        viewModelScope.launch {
            accountRepository.addAccount(AccountMapper.toEntity(account))
        }
    }

    fun deleteAccount(id: String) {
        viewModelScope.launch {
            accountRepository.deleteAccount(id)
        }
    }

    fun setActive(id: String) {
        viewModelScope.launch {
            accountRepository.setActiveAccount(id)
        }
    }
}
