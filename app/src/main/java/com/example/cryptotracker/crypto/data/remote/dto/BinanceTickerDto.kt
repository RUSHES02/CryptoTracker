package com.example.cryptotracker.crypto.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinanceTickerDto(

    @SerialName("s")
    val symbol: String,

    @SerialName("c")
    val currentPrice: String,

    @SerialName("P")
    val priceChangePercent: String
)
