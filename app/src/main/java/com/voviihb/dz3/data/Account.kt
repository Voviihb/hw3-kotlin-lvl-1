package com.voviihb.dz3.data

data class Account(
    val accountName: String,
    val accountLogo: Int,
    val totalMoney: Double
) : IAccount
