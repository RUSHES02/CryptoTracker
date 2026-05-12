package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.crypto.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun observeCoins(): Flow<List<Coin>>

    suspend fun refreshCoins()

    suspend fun startRealtimeUpdates()

    suspend fun stopRealtimeUpdates()
}