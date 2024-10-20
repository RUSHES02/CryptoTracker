package com.example.cryptotracker.crypto.presentation.coin_list

import com.example.cryptotracker.crypto.presentation.modal.CoinUi

sealed class CoinListAction {
	data class OnCoinClick(val coinUi: List<CoinUi>) : CoinListAction()
	data object OnRefresh: CoinListAction()
}