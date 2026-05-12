package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun observeCoins(): Flow<List<Coin>>

    suspend fun refreshCoins()

    suspend fun startRealtimeUpdates()
}