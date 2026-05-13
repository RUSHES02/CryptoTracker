package com.example.cryptotracker.crypto.presentation.modal

import androidx.annotation.DrawableRes
import com.example.cryptotracker.crypto.domain.model.Coin
import com.example.cryptotracker.core.presentation.getDrawableIdForCoin
import com.example.cryptotracker.crypto.presentation.coin_details.model.DataPoint
import java.text.NumberFormat

data class CoinUi(
    val symbol: String,
    val name: String,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val volume: DisplayableNumber,
    val high24h: DisplayableNumber,
    val low24h: DisplayableNumber,
    @DrawableRes
    val iconRes: Int,
    val coinPriceHistory: List<DataPoint> = emptyList()
)

data class DisplayableNumber(
	val value: Double,
	val formatted: String,
)

fun Coin.toCoinUi(): CoinUi {

    return CoinUi(
        symbol = symbol,
        name = name,
        priceUsd = price.toDisplayableNumber(),
        changePercent24Hr = changePercent24h.toDisplayableNumber(),
        volume = volume.toDisplayableNumber(),
        high24h = high24h.toDisplayableNumber(),
        low24h = low24h.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}
fun Double.toDisplayableNumber(): DisplayableNumber{
	val formatter = NumberFormat.getNumberInstance(java.util.Locale.getDefault()).apply {
		minimumFractionDigits = 2
		maximumFractionDigits = 2
	}
	return DisplayableNumber(
		value = this,
		formatted = formatter.format(this)
	)
}