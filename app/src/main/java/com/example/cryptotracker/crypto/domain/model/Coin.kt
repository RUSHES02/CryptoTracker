package com.example.cryptotracker.crypto.domain.model

data class Coin (
    val symbol: String,
    val name: String,
    val price: Double,
    val changePercent24h: Double,
    val volume: Double,
    val high24h: Double,
    val low24h: Double,
)