package com.voviihb.dz3

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voviihb.dz3.data.Account
import com.voviihb.dz3.data.AccountDomain
import com.voviihb.dz3.data.Bank
import com.voviihb.dz3.data.ResponseStatus
import kotlinx.coroutines.launch
import kotlin.random.Random

class AccountsViewModel : ViewModel() {
    private val EXCEPTION_OCCURRED = "Exception occurred!"

    private val mainRepo: MainRepo = MainRepo()

    private val _accountsList = mutableStateListOf<Account>()
    val accountsList: List<Account> = _accountsList

    private val _banksList = mutableStateListOf<Bank>()
    val banksList: List<Bank> = _banksList

    private val _totalMoney = mutableStateOf(0.0)
    val totalMoney: State<Double> = _totalMoney

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

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
                if (response.status == ResponseStatus.Success) {
                    if (!_accountsList.isEmpty()) {
                        _accountsList.clear()
                    }
                    for (account in response.accounts) {
                        _accountsList.add(
                            Account(
                                account.accountName,
                                account.accountLogo.logo,
                                account.totalMoney
                            )
                        )
                    }
                    _totalMoney.value = getTotalMoney(response.accounts)
                    _loading.value = false
                } else {
                    _accountsList += Account(
                        "Error",
                        R.drawable.error_icon,
                        0.0
                    )
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    fun getBanks(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getBanks(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    if (!_banksList.isEmpty()) {
                        _banksList.clear()
                    }
                    for (bank in response.banks) {
                        _banksList.add(
                            Bank(bank.bankName, bank.bankLogo.logo)
                        )
                    }
                    _loading.value = false
                } else {
                    _banksList += Bank("Error", R.drawable.error_icon)
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    fun getAccountsScreenData(userId: Int) {
        getAccounts(userId)
        getBanks(userId)
    }

    private fun getTotalMoney(accounts: List<AccountDomain>): Double {
        var total = 0.0
        for (account in accounts) {
            total += account.totalMoney
        }
        return total
    }


    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
