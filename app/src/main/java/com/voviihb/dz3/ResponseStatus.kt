package com.voviihb.dz3

sealed interface ResponseStatus {
    data object Success : ResponseStatus
    data class Error(val message: String) : ResponseStatus
}
