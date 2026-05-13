package com.example.cryptotracker.crypto.data.remote.networking

import com.example.cryptotracker.crypto.data.remote.dto.BinanceTickerDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BinanceApiDataSource(
    private val client: HttpClient
)  {

    suspend fun getTickers():
            List<BinanceTickerDto> {

        return client.get(
            "https://api.binance.com/api/v3/ticker/24hr"
        ).body()
    }
}