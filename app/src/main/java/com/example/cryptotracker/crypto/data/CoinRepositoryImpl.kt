package com.example.cryptotracker.crypto.data

import com.example.cryptotracker.crypto.data.local.dao.CoinDao
import com.example.cryptotracker.crypto.data.local.mapper.toCoin
import com.example.cryptotracker.crypto.data.remote.mapper.toEntity
import com.example.cryptotracker.crypto.data.remote.networking.BinanceSocketDataSource
import com.example.cryptotracker.crypto.data.remote.networking.CoinGeckoApi
import com.example.cryptotracker.crypto.domain.CoinRepository
import com.example.cryptotracker.crypto.domain.model.Coin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoinRepositoryImpl(
    private val api: CoinGeckoApi,
    private val dao: CoinDao,
    private val socket: BinanceSocketDataSource
) : CoinRepository {

    private var socketJob: Job? = null

    override fun observeCoins(): Flow<List<Coin>> {
        return dao.observeCoins()
            .map { entities ->
                entities.map { it.toCoin() }
            }
    }

    override suspend fun refreshCoins() {
        val remoteCoins = api.getTopCoins()
        val entities = remoteCoins.map {
            it.toEntity()
        }

        dao.upsertCoins(entities)
    }

    override suspend fun startRealtimeUpdates() {
        socketJob?.cancel()

        val symbols =
            dao.observeCoins()
                .map { coins ->
                    coins.map { it.binanceSymbol }
                }

        socketJob = CoroutineScope(
            Dispatchers.IO
        ).launch {
            symbols.collect { coinSymbols ->
                socket.observeTickerStream(
                    coinSymbols
                ).collect { ticker ->
                    dao.updatePrice(
                        symbol = ticker.symbol,
                        price = ticker.currentPrice.toDoubleOrNull()
                            ?: return@collect,
                        changePercent =
                            ticker.priceChangePercent.toDoubleOrNull()
                                ?: 0.0,
                        updatedAt =
                            System.currentTimeMillis()
                    )
                }
            }
        }
    }

    override suspend fun stopRealtimeUpdates() {
        socketJob?.cancel()
    }
}