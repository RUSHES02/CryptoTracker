package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.core.util.NetworkError
import com.example.cryptotracker.core.util.Result

interface CoinDataSource {
	suspend fun getCoins(): Result<List<Coin>, NetworkError>
}