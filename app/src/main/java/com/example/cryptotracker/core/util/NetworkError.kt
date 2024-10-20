package com.example.cryptotracker.core.util

enum class NetworkError: Error {
	REQUEST_TIMEOUT,
	TOO_MANY_REQUESTS,
	NO_INTERNET,
	SERVER_ERROR,
	SERIALIZATION,
	UNKNOWN_ERROR,
}