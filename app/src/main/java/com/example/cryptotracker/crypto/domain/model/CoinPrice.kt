package com.example.cryptotracker.crypto.domain.model

data class CoinPrice(
    val symbol: String,
    val price: Double,
    val changePercent: Double
)