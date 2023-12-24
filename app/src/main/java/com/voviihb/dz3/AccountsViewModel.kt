package com.voviihb.dz3

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountsViewModel : ViewModel() {
    private val dbRepo: DBRepo = DBRepo()

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

    fun getAccounts(userId: Int = 0) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response =
                    dbRepo.getAccounts(userId = userId)
                if (response.isSuccessful) {
                    _accountsList += response.accounts
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

    fun getBanks(userId: Int = 0) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response =
                    dbRepo.getBanks(userId = userId)
                if (response.isSuccessful) {
                    _banksList += response.banks
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

class DBRepo() {
    private val DELAY_TIME = 2000L
    private val logoList = listOf(
        R.drawable.tinkoff_logo,
        R.drawable.sber_logo,
        R.drawable.alfa_logo,
        R.drawable.cash_icon
    )

    suspend fun getAccounts(userId: Int = 0) = withContext(Dispatchers.IO) {
        val accountsList = mutableListOf<Account>()
        for (i in 1..20) {
            accountsList.add(
                Account(
                    "Account $i",
                    logoList[(i - 1) % 4],
                    1_000_000.0 / i
                )
            )
        }

        delay(DELAY_TIME)
        return@withContext AccountsResponse(true, accountsList)
    }

    suspend fun getBanks(userId: Int = 0) = withContext(Dispatchers.IO) {
        val banksList = mutableListOf(
            Bank("Tinkoff", R.drawable.tinkoff_logo),
            Bank("Sber", R.drawable.sber_logo),
            Bank("AlfaBank", R.drawable.alfa_logo)
        )

        delay(DELAY_TIME)
        return@withContext BanksResponse(true, banksList)

    }
}

private fun getTotalMoney(accounts: List<Account>): Double {
    var total = 0.0
    for (account in accounts) {
        total += account.totalMoney
    }
    return total
}