package com.example.note.account_module.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.account_module.domain.usecase.AccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
    val isRegistering: Boolean = false,
    val isRegistered: Boolean = false,
)

class RegisterViewModel(
    private val accountUseCase: AccountUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState>
        get() = _uiState

    private fun validateUsernamePassword(username: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty()) {
            showRegisterError("Username cannot be empty")
            return false
        }

        if (password.isEmpty()) {
            showRegisterError("Password cannot be empty")
            return false
        }

        // Example validation criteria: Username must be at least 4 characters
        if (username.length < 4) {
            showRegisterError("Username must be at least 4 characters long")
            return false
        }

        // Example validation criteria: Password must be at least 8 characters, with numbers and letters
        if (password.length < 8 || !password.matches(".*[0-9].*".toRegex()) || !password.matches(".*[a-zA-Z].*".toRegex())) {
            showRegisterError("Password must be at least 8 characters long and contain both letters and numbers")
            return false
        }

        if (password != confirmPassword) {
            showRegisterError("Password and confirm password miss matching")
            return false
        }


        return true
    }

    fun register() {
        viewModelScope.launch {
            val uiStateVal = _uiState.value
            val username = uiStateVal.username
            val password = uiStateVal.password
            val reEnterPassword = uiStateVal.confirmPassword

            _uiState.update { it.copy(isRegistering = true, errorMessage = null) }
            if (!validateUsernamePassword(username, password, reEnterPassword)) return@launch

            if (accountUseCase.isExistAccount(_uiState.value.username)) {
                showRegisterError("username is already exist")
                return@launch
            }
            accountUseCase.register(username, password)
            _uiState.update { it.copy(isRegistering = false, isRegistered = true, errorMessage = null ) }
        }
    }

    private fun showRegisterError(message: String) {
        _uiState.update { it.copy(isRegistering = false, errorMessage = message) }
    }

    fun enterUsername(username: String) {
        _uiState.update { it.copy(username = username, errorMessage = null) }
    }

    fun enterPassword(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun enterConfirmPassword(password: String) {
        _uiState.update { it.copy(confirmPassword = password, errorMessage = null) }
    }
}