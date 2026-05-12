package com.example.cryptotracker.crypto.data.local.mapper

import com.example.cryptotracker.crypto.data.local.entity.CoinEntity
import com.example.cryptotracker.crypto.domain.Coin

fun CoinEntity.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        imageUrl = imageUrl,
        marketCap = marketCap,
        price = price,
        changePercent24h = changePercent24h,
        binanceSymbol = binanceSymbol
    )
}

fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        imageUrl = imageUrl,
        marketCap = marketCap,
        price = price,
        changePercent24h = changePercent24h,
        binanceSymbol = binanceSymbol,
        lastUpdated = System.currentTimeMillis()
    )
}