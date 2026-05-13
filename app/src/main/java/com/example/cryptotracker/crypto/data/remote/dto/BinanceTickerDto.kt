package com.example.cryptotracker.crypto.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinanceTickerDto(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("lastPrice")
    val lastPrice: String,
    @SerialName("priceChangePercent")
    val priceChangePercent: String,
    @SerialName("volume")
    val volume: String,
    @SerialName("highPrice")
    val highPrice: String,
    @SerialName("lowPrice")
    val lowPrice: String
)
