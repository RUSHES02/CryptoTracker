package com.example.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptotracker.crypto.presentation.modal.CoinUi

@Composable
fun CoinListItem(
	conUi: CoinUi,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
){
	Row (
		modifier = modifier
			.clickable { onClick() }
			.padding(16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	){
		Icon(
			imageVector = ImageVector.vectorResource(id = conUi.iconRes),
			contentDescription = conUi.name,
			tint = MaterialTheme.colorScheme.primary,
			modifier = Modifier
				.size(85.dp)
		)
	}
}

@Preview
@Composable
private fun CoinListItemPreview(){

}