package com.example.cryptotracker.crypto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey
    val symbol: String,
    val name: String,
    val price: Double,
    val changePercent24h: Double,
    val volume: Double,
    val high24h: Double,
    val low24h: Double,
    val lastUpdated: Long
)