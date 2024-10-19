package com.plcoding.cryptotracker.crypto.presentation.modal

import androidx.annotation.DrawableRes

data class CoinUi (
	val id: String,
	val rank: String,
	val name: String,
	val symbol: String,
	val marketCapUsd: DisplayableNumber,
	val percentUsd: DisplayableNumber,
	val changePercent24Hr: DisplayableNumber,
	@DrawableRes val iconRes: Int
)

data class DisplayableNumber(
	val value: Double,
	val formatted: String,
)