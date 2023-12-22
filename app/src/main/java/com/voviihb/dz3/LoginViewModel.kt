package com.voviihb.dz3

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class LoginViewModel constructor(private val loginRepo: LoginRepo) : ViewModel() {
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
                    loginRepo.authUser(_loginFormState.value.email, _loginFormState.value.password)
                Log.d("taag", response.toString())
                if (response.isSuccessful) {
                    if (response.result) {
                        _isLogged.value = true
                        Log.d("!!!", "LOGGED")
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

class LoginRepo {
    private val DEFAULT_ACCOUNT_EMAIL = "mail@mail.ru"
    private val DEFAULT_ACCOUNT_PASSWORD = "Qwerty"
    private val DELAY_TIME = 2000L
    suspend fun authUser(email: String, password: String) = withContext(Dispatchers.IO) {
        val success = Random.nextBoolean()
        val result = (email == DEFAULT_ACCOUNT_EMAIL && password == DEFAULT_ACCOUNT_PASSWORD)

        delay(DELAY_TIME)
        return@withContext LoginResponse(isSuccessful = success, result = result)
    }
}