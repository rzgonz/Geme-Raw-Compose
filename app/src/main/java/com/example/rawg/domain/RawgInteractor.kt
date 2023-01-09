package com.example.rawg.domain

import com.example.rawg.domain.dto.GameDetailDto
import com.example.rawg.domain.dto.ListGameDto

interface RawgInteractor {

    suspend fun getListGame(params: Map<String, String>): ListGameDto
    suspend fun getDetailgame(gameId: String): GameDetailDto
    suspend fun getListGameFavorite(): List<GameDetailDto>
    suspend fun saveToFavorite(detailDto: GameDetailDto)
    suspend fun deleteFromFavorite(detailDto: GameDetailDto)
}