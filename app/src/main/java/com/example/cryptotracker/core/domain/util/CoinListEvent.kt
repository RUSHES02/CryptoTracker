package com.example.cryptotracker.core.domain.util

sealed interface CoinListEvent {
	data class Error(val error: NetworkError) : CoinListEvent
}