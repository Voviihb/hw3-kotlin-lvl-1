package com.voviihb.dz3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class MainRepo() {
    private val DEFAULT_ACCOUNT_EMAIL = "mail@mail.ru"
    private val DEFAULT_ACCOUNT_PASSWORD = "Qwerty"
    private val DELAY_TIME = 2000L
    private val logoList = listOf(
        R.drawable.tinkoff_logo,
        R.drawable.sber_logo,
        R.drawable.alfa_logo,
        R.drawable.cash_icon
    )

    suspend fun authUser(email: String, password: String) = withContext(Dispatchers.IO) {
        val success = true //Random.nextBoolean()
        val result = (email == DEFAULT_ACCOUNT_EMAIL && password == DEFAULT_ACCOUNT_PASSWORD)

        delay(DELAY_TIME)
        return@withContext LoginResponse(isSuccessful = success, result = result)
    }


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