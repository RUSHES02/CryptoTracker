package com.example.cryptotracker.crypto.data.local.mapper

import com.example.cryptotracker.crypto.data.local.entity.CoinEntity
import com.example.cryptotracker.crypto.domain.model.Coin

fun CoinEntity.toCoin(): Coin {
    return Coin(
        symbol = symbol,
        name = name,
        price = price,
        changePercent24h = changePercent24h,
        volume = volume,
        high24h = high24h,
        low24h = low24h,
    )
}