package com.example.cryptotracker.crypto.data

import com.example.cryptotracker.crypto.data.local.dao.CoinDao
import com.example.cryptotracker.crypto.data.local.mapper.toCoin
import com.example.cryptotracker.crypto.data.remote.mapper.toEntity
import com.example.cryptotracker.crypto.data.remote.networking.BinanceApiDataSource
import com.example.cryptotracker.crypto.data.remote.networking.BinanceSocketDataSource
import com.example.cryptotracker.crypto.domain.CoinRepository
import com.example.cryptotracker.crypto.domain.model.Coin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoinRepositoryImpl(
    private val api: BinanceApiDataSource,
    private val dao: CoinDao,
    private val socket: BinanceSocketDataSource
) : CoinRepository {

    private val repositoryScope =
        CoroutineScope(Dispatchers.IO)

    private var socketJob: Job? = null

    override fun observeCoins(): Flow<List<Coin>> {

        return dao.observeCoins()
            .map { entities ->
                entities
                    .filter {
                        it.symbol.endsWith("USDT")
                    }
                    .map { entity ->
                        entity.toCoin()
                    }
            }
    }

    override suspend fun refreshCoins() {

        val remoteTickers = api.getTickers()

        val entities =
            remoteTickers
                .filter {
                    it.symbol.endsWith("USDT")
                }
                .map { ticker ->
                    ticker.toEntity()
                }

        dao.upsertCoins(entities)
    }

    override suspend fun startRealtimeUpdates() {
        if (socketJob?.isActive == true) {
            return
        }

        socketJob = repositoryScope.launch {
            socket.observeTickerStream()
                .collect { tickers ->

                    tickers
                        .filter {
                            it.symbol.endsWith("USDT")
                        }
                        .forEach { ticker ->
                            dao.updateTicker(
                                symbol = ticker.symbol,
                                price = ticker.lastPrice
                                        .toDoubleOrNull()
                                        ?: return@forEach,
                                changePercent = ticker.priceChangePercent
                                        .toDoubleOrNull()
                                        ?: 0.0,
                                volume = ticker.volume
                                        .toDoubleOrNull()
                                        ?: 0.0,
                                high = ticker.highPrice
                                        .toDoubleOrNull()
                                        ?: 0.0,
                                low = ticker.lowPrice
                                        .toDoubleOrNull()
                                        ?: 0.0,
                                updatedAt = System.currentTimeMillis()
                            )
                        }
                }
        }
    }

    override suspend fun stopRealtimeUpdates() {
        socketJob?.cancel()
        socketJob = null
    }
}