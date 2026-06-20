package com.mugiwara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val server: String,
    val login: String,
    val password: String,
    val balance: Double,
    val equity: Double,
    val margin: Double,
    val freeMargin: Double,
    val isConnected: Boolean,
    val isActive: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
