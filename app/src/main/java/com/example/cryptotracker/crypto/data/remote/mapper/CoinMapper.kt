package com.example.cryptotracker.crypto.data.remote.mapper

import com.example.cryptotracker.crypto.data.local.entity.CoinEntity
import com.example.cryptotracker.crypto.data.remote.dto.BinanceTickerDto

fun BinanceTickerDto.toEntity(): CoinEntity {

    return CoinEntity(
        symbol = normalizeBinanceSymbol(symbol),
        name = symbol.removeSuffix("USDT"),
        price = lastPrice.toDoubleOrNull() ?: 0.0,
        changePercent24h = priceChangePercent.toDoubleOrNull() ?: 0.0,
        volume = volume.toDoubleOrNull() ?: 0.0,
        high24h = highPrice.toDoubleOrNull() ?: 0.0,
        low24h = lowPrice.toDoubleOrNull() ?: 0.0,
        lastUpdated = System.currentTimeMillis(),
    )
}

fun normalizeBinanceSymbol(
    symbol: String
): String {

    return symbol
        .removeSuffix("USDT")
        .removeSuffix("BUSD")
        .removeSuffix("FDUSD")
        .replace("1000", "")
        .uppercase()
}