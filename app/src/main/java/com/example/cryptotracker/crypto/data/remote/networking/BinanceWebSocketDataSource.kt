package com.example.cryptotracker.crypto.data.remote.networking

import com.example.cryptotracker.crypto.data.remote.dto.CoinTickerDto
import com.example.cryptotracker.crypto.domain.CoinWebSocketDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

class BinanceWebSocketDataSource : CoinWebSocketDataSource {

    private val client: HttpClient

    constructor(client: HttpClient) {
        this.client = client
    }

    override fun observeTickerStream(
        symbols: List<String>
    ): Flow<CoinTickerDto> = flow {

        val streamPath = symbols.joinToString("/") {
            "${it.lowercase()}usdt@ticker"
        }

        client.webSocket(
            method = HttpMethod.Get,
            host = "stream.binance.com",
            port = 9443,
            path = "/stream?streams=$streamPath"
        ) {

            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    val jsonObject = Json.parseToJsonElement(text).jsonObject
                    val data = jsonObject["data"]
                    val dto = Json.decodeFromJsonElement<CoinTickerDto>(
                        data!!
                    )
                    emit(dto)
                }
            }
        }
    }
}