package com.example.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.core.domain.util.CoinListEvent
import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.crypto.domain.CoinRepository
import com.example.cryptotracker.crypto.presentation.modal.CoinUi
import com.example.cryptotracker.crypto.presentation.modal.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val repository: CoinRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    init {
        observeCoins()
        viewModelScope.launch {
            refreshCoins()
            repository.startRealtimeUpdates()
        }
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }

            CoinListAction.OnRefresh -> {
                viewModelScope.launch {
                    refreshCoins()
                }
            }
        }
    }

    private fun observeCoins() {
        viewModelScope.launch {
            repository.observeCoins()
                .collect { coins ->
                    _state.update {
                        it.copy(
                            loading = false,
                            coins = coins.map {
                                it.toCoinUi()
                            }
                        )
                    }
                }
        }
    }

    private suspend fun refreshCoins() {
        try {
            _state.update {
                it.copy(loading = true)
            }
            repository.refreshCoins()

        } catch (e: Exception) {
            _state.update {
                it.copy(loading = false)
            }

            _events.send(
                CoinListEvent.Error(
                    NetworkError.UNKNOWN_ERROR
                )
            )
        }
    }

    private fun selectCoin(
        coinUi: CoinUi
    ) {
        _state.update {
            it.copy(
                selectedCoin = coinUi
            )
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelScope.launch {
            repository.stopRealtimeUpdates()
        }
    }
}