package com.voviihb.dz3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val mainRepo: MainRepo = MainRepo()

    private val _loginFormState = MutableStateFlow(LoginForm("", ""))
    val loginFormState: StateFlow<LoginForm> = _loginFormState

    private val _isLogged = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> = _isLogged

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun login() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepo.authUser(_loginFormState.value.email, _loginFormState.value.password)
                if (response.isSuccessful) {
                    if (response.result) {
                        _isLogged.value = true
                    } else {
                        onError("Wrong email or password!")
                    }
                    _loading.value = false
                } else {
                    onError("Login error occurred")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Exception occurred!")
            }
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }

    fun clearError() {
        _errorMessage.value = ""
    }

    fun emailChanged(value: String) {
        _loginFormState.value = _loginFormState.value.copy(
            email = value
        )
    }

    fun passwordChanged(value: String) {
        _loginFormState.value = _loginFormState.value.copy(
            password = value
        )
    }


}


data class LoginForm(val email: String = "", val password: String = "")

data class LoginResponse(val isSuccessful: Boolean, val result: Boolean)
