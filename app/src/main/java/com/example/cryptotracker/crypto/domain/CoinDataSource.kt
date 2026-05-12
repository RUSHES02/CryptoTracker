package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.crypto.data.remote.dto.CoinDto
import com.example.cryptotracker.crypto.data.remote.dto.CoinHistoryDto

interface CoinDataSource {

    suspend fun getTopCoins(): List<CoinDto>

    suspend fun getCoinHistory(
        coinId: String
    ): List<CoinHistoryDto>
}