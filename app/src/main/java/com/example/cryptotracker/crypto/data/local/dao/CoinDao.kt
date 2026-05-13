package com.example.cryptotracker.crypto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptotracker.crypto.data.local.entity.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("""
        SELECT * FROM coins
        ORDER BY volume DESC
    """)
    fun observeCoins(): Flow<List<CoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCoins(
        coins: List<CoinEntity>
    )

    @Query("""
        UPDATE coins
        SET price = :price,
            changePercent24h = :changePercent,
            volume = :volume,
            high24h = :high,
            low24h = :low,
            lastUpdated = :updatedAt
        WHERE symbol = :symbol
    """)
    suspend fun updateTicker(
        symbol: String,
        price: Double,
        changePercent: Double,
        volume: Double,
        high: Double,
        low: Double,
        updatedAt: Long
    )

    @Query("DELETE FROM coins")
    suspend fun clearCoins()
}