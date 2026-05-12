package com.example.cryptotracker.crypto.data

import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.core.domain.util.Result
import com.example.cryptotracker.crypto.domain.Coin
import com.example.cryptotracker.crypto.domain.CoinPrice
import com.example.cryptotracker.crypto.domain.CoinRepository
import kotlinx.coroutines.flow.Flow

class CoinRepositoryImpl(): CoinRepository {

    override fun observeCoins(): Flow<List<Coin>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshCoins() {
        TODO("Not yet implemented")
    }

    override suspend fun startRealtimeUpdates() {
        TODO("Not yet implemented")
    }

}