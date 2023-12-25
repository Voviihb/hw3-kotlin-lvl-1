package com.voviihb.dz3

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class AccountsViewModel : ViewModel() {
    private val mainRepo: MainRepo = MainRepo()

    private val _accountsList = mutableStateListOf<Account>()
    val accountsList: List<Account> = _accountsList

    private val _banksList = mutableStateListOf<Bank>()
    val banksList: List<Bank> = _banksList

    private val _totalMoney = mutableStateOf(0.0)
    val totalMoney: State<Double> = _totalMoney

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    init {
        getAccountsScreenData(getCurrentUserId())
    }

    fun getCurrentUserId(): Int {
        return Random.nextInt(0, 1000)
    }

    fun getAccounts(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepo.getAccounts(userId = userId)
                if (response.isSuccessful) {
                    if (!_accountsList.isEmpty()) {
                        _accountsList.clear()
                    }
                    _accountsList.addAll(response.accounts)
                    _totalMoney.value = getTotalMoney(response.accounts)
                    _loading.value = false
                } else {
                    _accountsList += Account(
                        "Error",
                        R.drawable.error_icon,
                        0.0
                    )
                    onError("Fetching accounts error!")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Exception occurred!")
            }
        }
    }

    fun getBanks(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getBanks(userId = userId)
                if (response.isSuccessful) {
                    if (!_banksList.isEmpty()) {
                        _banksList.clear()
                    }
                    _banksList.addAll(response.banks)
                    _loading.value = false
                } else {
                    _banksList += Bank("Error", R.drawable.error_icon)
                    onError("Fetching banks error!")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Exception occurred!")
            }
        }
    }

    fun getAccountsScreenData(userId: Int) {
        getAccounts(userId)
        getBanks(userId)
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }

    fun clearError() {
        _errorMessage.value = ""
    }
}

data class AccountsResponse(val isSuccessful: Boolean, val accounts: List<Account>)
data class BanksResponse(val isSuccessful: Boolean, val banks: List<Bank>)


private fun getTotalMoney(accounts: List<Account>): Double {
    var total = 0.0
    for (account in accounts) {
        total += account.totalMoney
    }
    return total
}