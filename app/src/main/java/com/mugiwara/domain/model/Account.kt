package com.mugiwara.domain.model

data class Account(
    val id: String,
    val name: String,
    val server: String,
    val login: String,
    val balance: Double,
    val equity: Double,
    val margin: Double,
    val freeMargin: Double,
    val isConnected: Boolean,
    val isActive: Boolean
)
