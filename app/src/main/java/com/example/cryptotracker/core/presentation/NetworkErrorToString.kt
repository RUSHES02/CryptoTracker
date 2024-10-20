package com.example.cryptotracker.core.presentation

import android.content.Context
import com.example.cryptotracker.core.domain.util.NetworkError
import com.example.cryptotracker.R


fun NetworkError.toString(context: Context): String{
	val resId = when(this){
		NetworkError.REQUEST_TIMEOUT -> R.string.error_time_out
		NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_request
		NetworkError.NO_INTERNET -> R.string.error_no_internet
		NetworkError.SERVER_ERROR -> R.string.error_unknown
		NetworkError.SERIALIZATION -> R.string.error_serialization
		NetworkError.UNKNOWN_ERROR -> R.string.error_unknown
	}
	return context.getString(resId)
}