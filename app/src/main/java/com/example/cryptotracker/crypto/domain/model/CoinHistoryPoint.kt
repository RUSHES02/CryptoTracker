package com.example.cryptotracker.crypto.domain.model

import java.time.ZonedDateTime

data class CoinHistoryPoint(
    val price: Double,
    val dateTime: ZonedDateTime
)