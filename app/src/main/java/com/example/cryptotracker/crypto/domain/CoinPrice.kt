package com.example.cryptotracker.crypto.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val symbol: String,
	val priceUsd: Double,
    val changePercent: Double
)
