package com.example.cryptotracker.crypto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptotracker.crypto.data.local.entity.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("SELECT * FROM coins ORDER BY rank ASC")
    fun observeCoins(): Flow<List<CoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCoins(
        coins: List<CoinEntity>
    )

    @Query("""
        UPDATE coins
        SET price = :price,
            changePercent24h = :changePercent,
            lastUpdated = :updatedAt
        WHERE binanceSymbol = :symbol
    """)
    suspend fun updatePrice(
        symbol: String,
        price: Double,
        changePercent: Double,
        updatedAt: Long
    )

    @Query("DELETE FROM coins")
    suspend fun clear()
}