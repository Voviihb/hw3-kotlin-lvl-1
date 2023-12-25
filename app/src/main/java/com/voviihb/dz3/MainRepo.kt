package com.voviihb.dz3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class MainRepo() {
    private val DEFAULT_ACCOUNT_EMAIL = "mail@mail.ru"
    private val DEFAULT_ACCOUNT_PASSWORD = "Qwerty"
    private val DELAY_TIME = 2000L

    suspend fun authUser(email: String, password: String) = withContext(Dispatchers.IO) {
        val success = ResponseStatus.Success
        val result = (email == DEFAULT_ACCOUNT_EMAIL && password == DEFAULT_ACCOUNT_PASSWORD)

        delay(DELAY_TIME)
        return@withContext LoginResponse(status = success, result = result)
    }


    suspend fun getAccounts(userId: Int = 0) = withContext(Dispatchers.IO) {
        val accountsList = mutableListOf<AccountDomain>()
        for (i in 1..20) {
            accountsList.add(
                AccountDomain(
                    "Account $i",
                    when ((i - 1) % 4) {
                        0 -> AccountLogo.TINKOFF
                        1 -> AccountLogo.SBER
                        2 -> AccountLogo.AlFA
                        else -> AccountLogo.CASH
                    },
                    1_000_000.0 / i
                )
            )
        }

        delay(DELAY_TIME)
        return@withContext AccountsResponse(ResponseStatus.Success, accountsList)
    }

    suspend fun getBanks(userId: Int = 0) = withContext(Dispatchers.IO) {
        val banksList = mutableListOf(
            BankDomain("Tinkoff", BankLogo.TINKOFF),
            BankDomain("Sber", BankLogo.SBER),
            BankDomain("AlfaBank", BankLogo.AlFA)
        )

        delay(DELAY_TIME)
        return@withContext BanksResponse(ResponseStatus.Success, banksList)

    }
}