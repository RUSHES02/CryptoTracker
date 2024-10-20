package com.example.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.core.util.onError
import com.example.cryptotracker.core.util.onSuccess
import com.example.cryptotracker.crypto.domain.CoinDataSource
import com.example.cryptotracker.crypto.presentation.modal.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
	private val coinDataSource: CoinDataSource
): ViewModel() {
	
	private val _state = MutableStateFlow(CoinListState())
	val state = _state
		.onStart {
			loadCoins()
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000L),
			initialValue = CoinListState()
		)
	
	fun onAction(action: CoinListAction){
		when(action){
			is CoinListAction.OnCoinClick -> {
			
			}
			is CoinListAction.OnRefresh -> {
				loadCoins()
			}
		}
	}
	
	private fun loadCoins(){
		viewModelScope.launch {
			_state.update { it.copy(loading = true) }
			coinDataSource
				.getCoins()
				.onSuccess { coins ->
					_state.update { it.copy(
						loading = false,
						coins = coins.map { it.toCoinUi() }
						) }
				}
				.onError { error ->
					_state.update { it.copy(loading = false) }
				}
		}
	}
}