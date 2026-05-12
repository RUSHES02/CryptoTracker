package com.example.cryptotracker.crypto.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto (
	val data: List<BinanceTickerDto>
)