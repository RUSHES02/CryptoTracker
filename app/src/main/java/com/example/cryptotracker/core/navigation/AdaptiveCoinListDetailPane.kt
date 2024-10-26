package com.example.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.cryptotracker.core.domain.util.CoinListEvent
import com.example.cryptotracker.core.presentation.toString
import com.example.cryptotracker.core.presentation.util.ObserveAsEvents
import com.example.cryptotracker.crypto.presentation.coin_details.CoinDetailsScreen
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
	coinListViewModel: CoinListViewModel = koinViewModel(),
	modifier: Modifier = Modifier
){
	val state by coinListViewModel.state.collectAsState()
	val context = LocalContext.current
	ObserveAsEvents(events = coinListViewModel.events) {event ->
		when (event) {
			is CoinListEvent.Error -> {
				Toast.makeText(
					context,
					event.error.toString(context),
					Toast.LENGTH_LONG
				).show()
			}
		}
	}
	
	val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
	NavigableListDetailPaneScaffold(
		navigator = navigator,
		listPane = {
			AnimatedPane{
				CoinListScreen(
					state = state,
					onAction = {action ->
						coinListViewModel.onAction(action)
						when(action){
							is CoinListAction.OnCoinClick ->{
								navigator.navigateTo(
									pane = ListDetailPaneScaffoldRole.Detail
								)
							}
							is CoinListAction.OnRefresh ->{
							
							}
						}
					}
				)
			}
		},
		detailPane = {
			CoinDetailsScreen(state = state)
		},
		modifier = modifier
	)
}