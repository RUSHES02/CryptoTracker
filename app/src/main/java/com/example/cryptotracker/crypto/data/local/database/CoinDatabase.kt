package com.example.cryptotracker.crypto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptotracker.crypto.data.local.dao.CoinDao
import com.example.cryptotracker.crypto.data.local.entity.CoinEntity

@Database(
    entities = [CoinEntity::class],
    version = 1
)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}