package com.voviihb.dz3

import com.voviihb.dz3.data.AccountDomain
import com.voviihb.dz3.data.AccountsResponse
import com.voviihb.dz3.data.BankDomain
import com.voviihb.dz3.data.BanksResponse
import com.voviihb.dz3.data.ItemLogo
import com.voviihb.dz3.data.LoginResponse
import com.voviihb.dz3.data.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class MainRepo() {
    private val DEFAULT_ACCOUNT_EMAIL = "mail@mail.ru"
    private val DEFAULT_ACCOUNT_PASSWORD = "Qwerty"
    private val DELAY_TIME = 2000L

    suspend fun authUser(email: String, password: String) = withContext(Dispatchers.IO) {
        val status = ResponseStatus.Success //ResponseStatus.Error("Login error occurred!")
        val result = (email == DEFAULT_ACCOUNT_EMAIL && password == DEFAULT_ACCOUNT_PASSWORD)

        delay(DELAY_TIME)
        return@withContext LoginResponse(status = status, result = result)
    }


    suspend fun getAccounts(userId: Int = 0) = withContext(Dispatchers.IO) {
        val accountsList = mutableListOf<AccountDomain>()
        val status = ResponseStatus.Success //ResponseStatus.Error("Fetching accounts error!")
        for (i in 1..20) {
            accountsList.add(
                AccountDomain(
                    "Account $i",
                    when ((i - 1) % 4) {
                        0 -> ItemLogo.TINKOFF
                        1 -> ItemLogo.SBER
                        2 -> ItemLogo.AlFA
                        else -> ItemLogo.CASH
                    },
                    1_000_000.0 / i
                )
            )
        }

        delay(DELAY_TIME)
        return@withContext AccountsResponse(status, accountsList)
    }

    suspend fun getBanks(userId: Int = 0) = withContext(Dispatchers.IO) {
        val banksList = mutableListOf(
            BankDomain("Tinkoff", ItemLogo.TINKOFF),
            BankDomain("Sber", ItemLogo.SBER),
            BankDomain("AlfaBank", ItemLogo.AlFA)
        )
        val status = ResponseStatus.Success //ResponseStatus.Error("Fetching banks error!")

        delay(DELAY_TIME)
        return@withContext BanksResponse(status, banksList)

    }
}