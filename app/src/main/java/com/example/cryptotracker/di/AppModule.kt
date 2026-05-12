package com.example.cryptotracker.di

import androidx.room.Room
import com.example.cryptotracker.core.data.networking.HttpClientFactory
import com.example.cryptotracker.crypto.data.CoinRepositoryImpl
import com.example.cryptotracker.crypto.data.local.dao.CoinDao
import com.example.cryptotracker.crypto.data.local.database.CoinDatabase
import com.example.cryptotracker.crypto.domain.CoinRepository
import com.example.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
	single { HttpClientFactory.create(CIO.create()) }
//	singleOf(::RemoteCoinDataSource).bind<RemoteCoinDataSource>()
	viewModelOf(::CoinListViewModel)

    single<CoinDatabase> {

        Room.databaseBuilder(
            androidContext(),
            CoinDatabase::class.java,
            "coin_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<CoinDao>{get<CoinDatabase>().coinDao()}
}

val repositoryModule = module {

    single<CoinRepository> {
        CoinRepositoryImpl(
            api = get(),
            dao = get(),
            socket = get()
        )
    }
}