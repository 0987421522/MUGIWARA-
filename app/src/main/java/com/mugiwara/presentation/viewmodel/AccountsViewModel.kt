package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.domain.model.Account
import com.mugiwara.domain.usecase.*
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getActiveAccountUseCase: GetActiveAccountUseCase,
    private val addAccountUseCase: AddAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val setActiveAccountUseCase: SetActiveAccountUseCase
) : ViewModel() {

    private val _accounts = MutableStateFlow<UiState<List<Account>>>(UiState.Loading)
    val accounts: StateFlow<UiState<List<Account>>> = _accounts.asStateFlow()

    private val _activeAccount = MutableStateFlow<UiState<Account?>>(UiState.Loading)
    val activeAccount: StateFlow<UiState<Account?>> = _activeAccount.asStateFlow()

    private val _operationResult = MutableSharedFlow<String>()
    val operationResult: SharedFlow<String> = _operationResult.asSharedFlow()

    init {
        loadAccounts()
        loadActiveAccount()
    }

    fun loadAccounts() {
        viewModelScope.launch {
            getAccountsUseCase()
                .onStart { _accounts.value = UiState.Loading }
                .catch { e -> _accounts.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { accounts ->
                    _accounts.value = UiState.Success(accounts)
                }
        }
    }

    fun loadActiveAccount() {
        viewModelScope.launch {
            getActiveAccountUseCase()
                .onStart { _activeAccount.value = UiState.Loading }
                .catch { e -> _activeAccount.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { account ->
                    _activeAccount.value = UiState.Success(account)
                }
        }
    }

    fun addAccount(account: Account) {
        viewModelScope.launch {
            try {
                addAccountUseCase(account)
                _operationResult.emit("Account added successfully")
                loadAccounts()
            } catch (e: Exception) {
                _operationResult.emit("Failed to add account: ${e.message}")
            }
        }
    }

    fun deleteAccount(id: String) {
        viewModelScope.launch {
            try {
                deleteAccountUseCase(id)
                _operationResult.emit("Account deleted successfully")
                loadAccounts()
            } catch (e: Exception) {
                _operationResult.emit("Failed to delete account: ${e.message}")
            }
        }
    }

    fun setActiveAccount(id: String) {
        viewModelScope.launch {
            try {
                setActiveAccountUseCase(id)
                _operationResult.emit("Account activated successfully")
                loadActiveAccount()
            } catch (e: Exception) {
                _operationResult.emit("Failed to activate account: ${e.message}")
            }
        }
    }
}
