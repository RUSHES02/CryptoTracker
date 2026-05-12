package com.example.cryptotracker.crypto.data.remote.networking

import com.example.cryptotracker.crypto.data.remote.dto.BinanceTickerDto
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

class BinanceSocketDataSource(
    private val client: HttpClient
) {

    fun observeTickerStream(
        symbols: List<String>
    ): Flow<BinanceTickerDto> = flow {

        val streams = symbols.joinToString("/") {
            "${it.lowercase()}@ticker"
        }

        client.webSocket(
            method = HttpMethod.Get,
            host = "stream.binance.com",
            port = 9443,
            path = "/stream?streams=$streams"
        ) {
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    val root = Json.parseToJsonElement(text)
                            .jsonObject

                    val data = root["data"]
                    val dto = Json.decodeFromJsonElement<BinanceTickerDto>(data!!)

                    emit(dto)
                }
            }
        }
    }
}