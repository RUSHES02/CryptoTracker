package com.example.cryptotracker.crypto.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinResponseDto (
	val data: List<CoinDto>
)