package com.example.cryptotracker.di

import androidx.room.Room
import com.example.cryptotracker.core.data.networking.HttpClientFactory
import com.example.cryptotracker.crypto.data.local.database.CoinDatabase
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
	single { HttpClientFactory.create(CIO.create()) }
//	singleOf(::RemoteCoinDataSource).bind<RemoteCoinDataSource>()
	viewModelOf(::CoinListViewModel)
}


val databaseModule = module {

    single<CoinDatabase> {

        Room.databaseBuilder(
            androidContext(),
            CoinDatabase::class.java,
            "coin_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

//    single<CoinDao>{get<CoinDatabase>().coinDao()}
}