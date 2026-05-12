package com.example.cryptotracker.crypto.domain

import com.example.cryptotracker.crypto.data.remote.dto.BinanceTickerDto
import kotlinx.coroutines.flow.Flow

interface CoinWebSocketDataSource {

    fun observeTickerStream(
        symbols: List<String>
    ): Flow<BinanceTickerDto>
}