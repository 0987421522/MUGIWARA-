package com.mugiwara.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticks")
data class TickEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val symbol: String,
    val bid: Double,
    val ask: Double,
    val timestamp: Long
)
