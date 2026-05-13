package com.example.cryptotracker.crypto.data.remote.networking

import com.example.cryptotracker.crypto.data.remote.dto.BinanceTickerDto
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class BinanceSocketDataSource(
    private val client: HttpClient
) {

    fun observeTickerStream(): Flow<List<BinanceTickerDto>> = flow {
        client.webSocket(
            urlString =
                "wss://stream.binance.com:9443/ws/!ticker@arr"
        ) {
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    val tickers =
                        Json.decodeFromString<
                                List<BinanceTickerDto>
                                >(text)

                    emit(tickers)
                }
            }
        }
    }
}