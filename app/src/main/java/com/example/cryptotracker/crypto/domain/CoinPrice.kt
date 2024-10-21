package com.example.cryptotracker.crypto.domain

import java.time.ZonedDateTime

data class CoinPrice(
	val dateTime: ZonedDateTime,
	val priceUsd: Double
)
