package com.example.cryptotracker.crypto.data.remote.mapper

import com.example.cryptotracker.crypto.data.remote.dto.BinanceTickerDto
import com.example.cryptotracker.crypto.data.remote.dto.CoinDto
import com.example.cryptotracker.crypto.domain.Coin
import com.example.cryptotracker.crypto.domain.CoinPrice

fun CoinDto.toCoin(): Coin {
	return Coin(
		id = id,
        symbol = symbol,
        name = name,
        priceUsd = priceUsd,
        changePercent = changePercent
	)
}

fun BinanceTickerDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        symbol = symbol,
        priceUsd = currentPrice.toDouble(),
        changePercent = priceChangePercent.toDouble()
    )
}