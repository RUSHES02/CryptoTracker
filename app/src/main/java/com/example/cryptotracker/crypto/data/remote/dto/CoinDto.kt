package com.example.cryptotracker.crypto.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    @SerialName("market_cap_rank")
    val rank: Int?,
    @SerialName("current_price")
    val currentPrice: Double?,
    @SerialName("market_cap")
    val marketCap: Double?,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?
)