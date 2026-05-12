package com.example.cryptotracker.crypto.data.remote.networking

import com.example.cryptotracker.crypto.data.remote.dto.CoinDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CoinGeckoApi(
    private val client: HttpClient
)  {

    suspend fun getTopCoins(): List<CoinDto> {

        return client.get(
            "https://api.coingecko.com/api/v3/coins/markets"
        ) {
            parameter("vs_currency", "usd")
            parameter("order", "market_cap_desc")
            parameter("per_page", 100)
            parameter("page", 1)
            parameter("sparkline", false)
        }.body()
    }
}