package com.example.cryptotracker.crypto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val marketCap: Double,
    val price: Double,
    val changePercent24h: Double,
    val binanceSymbol: String,
    val lastUpdated: Long
)