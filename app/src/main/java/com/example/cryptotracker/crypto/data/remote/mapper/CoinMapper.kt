package com.example.cryptotracker.crypto.data.remote.mapper

import com.example.cryptotracker.crypto.data.local.entity.CoinEntity
import com.example.cryptotracker.crypto.data.remote.dto.BinanceTickerDto
import com.example.cryptotracker.crypto.data.remote.dto.CoinDto
import com.example.cryptotracker.crypto.domain.model.CoinPrice


fun CoinDto.toEntity(): CoinEntity {
    val uppercaseSymbol = symbol.uppercase()

    return CoinEntity(
        id = id,
        rank = rank ?: 0,
        name = name,
        symbol = uppercaseSymbol,
        imageUrl = image.orEmpty(),
        marketCap = marketCap ?: 0.0,
        price = currentPrice ?: 0.0,
        changePercent24h = priceChangePercentage24h ?: 0.0,
        binanceSymbol = "${uppercaseSymbol}USDT",
        lastUpdated = System.currentTimeMillis()
    )
}

fun BinanceTickerDto.toPriceUpdate(): CoinPrice {

    return CoinPrice(
        symbol = symbol,
        price = currentPrice.toDoubleOrNull() ?: 0.0,
        changePercent =
            priceChangePercent.toDoubleOrNull() ?: 0.0
    )
}