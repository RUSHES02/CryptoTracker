package com.example.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.example.cryptotracker.crypto.presentation.modal.CoinUi

@Immutable
data class CoinListState(
	val loading : Boolean = false,
	val coins : List<CoinUi> = emptyList(),
	val selectedCoin: CoinUi? = null,
	)