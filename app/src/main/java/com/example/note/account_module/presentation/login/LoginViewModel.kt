package com.example.note.account_module.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.account_module.domain.model.Account
import com.example.note.account_module.domain.usecase.AccountUseCase
import com.example.note.core.sharepreference.SharePreferenceUtil.currentLoginAccountId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoggingIn: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false
)

class LoginViewModel(
    private val accountUseCase: AccountUseCase
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            val uiStateVal = _uiState.value
            val username = uiStateVal.username
            val password = uiStateVal.password

            if (!isInputValid(username, password)) {
                _uiState.update { it.copy(isLoggingIn = false, errorMessage = "Username or password is not empty") }
                return@launch
            }

            _uiState.update { it.copy(isLoggingIn = true, errorMessage = null) }
            val account: Account? = accountUseCase.login(username, password)
            if (account == null) {
                onLoginError()
                return@launch
            }
            onLoginSuccess(account)
        }
    }

    private fun onLoginSuccess(account: Account) {
        _uiState.update {
            it.copy(isLoggedIn = true, isLoggingIn = false, errorMessage = null)
        }
        currentLoginAccountId = account.accountId
    }

    private fun onLoginError() {
        _uiState.update {
            it.copy(isLoggingIn = false, errorMessage = "Invalid username or password")
        }
    }

    fun enterUsername(username: String) {
        _uiState.update { it.copy(username = username, errorMessage = null) }
    }

    fun enterPassword(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    private fun isInputValid(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank()) {
            return false
        }
        return true
    }

}