package com.example.cryptotracker.crypto.domain.model

data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val marketCap: Double,
    val price: Double,
    val changePercent24h: Double,
    val binanceSymbol: String
)