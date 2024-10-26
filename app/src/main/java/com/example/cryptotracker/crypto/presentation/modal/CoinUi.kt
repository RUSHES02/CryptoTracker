package com.example.cryptotracker.crypto.presentation.modal

import androidx.annotation.DrawableRes
import com.example.cryptotracker.crypto.domain.Coin
import com.example.cryptotracker.core.presentation.getDrawableIdForCoin
import com.example.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.example.cryptotracker.crypto.presentation.coin_details.DataPoint
import java.text.NumberFormat

data class CoinUi (
	val id: String,
	val rank: Int,
	val name: String,
	val symbol: String,
	val marketCapUsd: DisplayableNumber,
	val priceUsd: DisplayableNumber,
	val changePercent24Hr: DisplayableNumber,
	@DrawableRes val iconRes: Int,
	val coinPriceHistory: List<DataPoint> = emptyList()
)

data class DisplayableNumber(
	val value: Double,
	val formatted: String,
)

fun Coin.toCoinUi(): CoinUi {
	return CoinUi(
		id = id,
		rank = rank,
		name = name,
		symbol = symbol,
		marketCapUsd = marketCapUsd.toDisplayableNumber(),
		priceUsd = priceUsd.toDisplayableNumber(),
		changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
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