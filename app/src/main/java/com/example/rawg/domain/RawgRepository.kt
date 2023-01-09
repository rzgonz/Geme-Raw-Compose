package com.example.rawg.domain

import com.example.rawg.data.local.RawgLocalDataSource
import com.example.rawg.data.local.entity.GameDetail
import com.example.rawg.data.remote.RawgRemoteDataSource

class RawgRepository(
    private val rawgRemoteDataSource: RawgRemoteDataSource,
    private val rawgLocalDataSource: RawgLocalDataSource
) {

    suspend fun getListGame(params: Map<String, String>) = rawgRemoteDataSource.getListGame(params)

    suspend fun getDetailGame(gameid: String) = rawgRemoteDataSource.getDetail(
        gameid
    )

    suspend fun getListGameFavorite() = rawgLocalDataSource.getListFavoriteGame()


   suspend fun addGameToFavorite(gameDetail: GameDetail) {
        rawgLocalDataSource.addGameToFavorite(gameDetail)
    }

    suspend fun removeGameFromFavorite(gameDetail: GameDetail) {
        rawgLocalDataSource.removeGameFromFavorite(gameDetail)
    }


}