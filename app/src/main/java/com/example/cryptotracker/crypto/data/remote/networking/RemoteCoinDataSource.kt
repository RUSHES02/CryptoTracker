//package com.example.cryptotracker.crypto.data.remote.networking
//
//import com.example.cryptotracker.core.data.networking.constructUrl
//import com.example.cryptotracker.core.data.networking.safeCall
//import com.example.cryptotracker.core.domain.util.NetworkError
//import com.example.cryptotracker.core.domain.util.Result
//import com.example.cryptotracker.core.domain.util.map
//import com.example.cryptotracker.crypto.data.remote.dto.CoinHistoryDto
//import com.example.cryptotracker.crypto.data.remote.dto.CoinResponseDto
//import com.example.cryptotracker.crypto.data.remote.mapper.toCoin
//import com.example.cryptotracker.crypto.domain.Coin
//import com.example.cryptotracker.crypto.domain.CoinDataSource
//import com.example.cryptotracker.crypto.domain.CoinPrice
//import io.ktor.client.HttpClient
//import io.ktor.client.request.get
//import io.ktor.client.request.parameter
//import java.time.ZoneId
//import java.time.ZonedDateTime
//import kotlin.collections.map
//
//class RemoteCoinDataSource(
//    private val httpClient: HttpClient,
//): CoinDataSource {
//	override suspend fun getTopCoins(): Result<List<Coin>, NetworkError> {
//		return safeCall<CoinResponseDto> {
//            httpClient.get(
//                urlString = constructUrl("/assets")
//            )
//        }.map { response ->
//			response.data.map { it.toCoin() }
//		}
//	}
//
//	override suspend fun getCoinDetails(
//        coinId: String,
//        start: ZonedDateTime,
//        end: ZonedDateTime
//	): Result<List<CoinPrice>, NetworkError> {
//
//		val startMillis = start
//			.withZoneSameInstant(ZoneId.of("UTC"))
//			.toInstant()
//			.toEpochMilli()
//
//		val endMillis = end
//			.withZoneSameInstant(ZoneId.of("UTC"))
//			.toInstant()
//			.toEpochMilli()
//
//		return safeCall<CoinHistoryDto> {
//            httpClient.get(
//                urlString = constructUrl("/assets/$coinId/history")
//            ) {
//                parameter("interval", "h6")
//                parameter("start", startMillis)
//                parameter("end", endMillis)
//            }
//        }.map { response ->
//			response.data.map { data -> data.() }
//		}
//	}
//}