package com.example.rawg.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM GameDetail")
    suspend fun getAll(): List<GameDetail>

    @Query("SELECT * FROM GameDetail WHERE gameId IN (:gameIds)")
    fun loadAllByIds(gameIds: IntArray): List<GameDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg gameDetail: GameDetail)

    @Delete
    suspend fun delete(gameDetail: GameDetail)
}