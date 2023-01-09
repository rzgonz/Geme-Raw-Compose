package com.example.rawg.data.local

import com.example.rawg.data.local.entity.GameDao
import com.example.rawg.data.local.entity.GameDetail

class RawgLocalDataSource(private val gameDao: GameDao) {

    suspend fun getListFavoriteGame() = gameDao.getAll()

    suspend fun addGameToFavorite(gameDetail: GameDetail) {
        gameDao.insertAll(gameDetail)
    }

   suspend fun removeGameFromFavorite(gameDetail: GameDetail) {
        gameDao.delete(gameDetail)
    }

}