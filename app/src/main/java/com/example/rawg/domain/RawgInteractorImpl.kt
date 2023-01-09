package com.example.rawg.domain

import com.example.rawg.domain.dto.GameDetailDto
import com.example.rawg.domain.dto.ListGameDto

class RawgInteractorImpl(
    private val rawgRepository: RawgRepository,
) : RawgInteractor {
    override suspend fun getListGame(params: Map<String, String>): ListGameDto {
        val data = rawgRepository.getListGame(params)
        return DataMapper.listGameResponseToDto(data)
    }

    override suspend fun getDetailgame(gameId: String): GameDetailDto {
        val data = rawgRepository.getDetailGame(gameId)
        return DataMapper.gameDetailResponseToDto(data)
    }

    override suspend fun getListGameFavorite(): List<GameDetailDto> {
        val data = rawgRepository.getListGameFavorite().map {
            DataMapper.gameDetailToGameDetailDto(it)
        }
        return data
    }

    override suspend  fun saveToFavorite(detailDto: GameDetailDto) {
        val data = DataMapper.gameDetailDtoToEntity(detailDto)
        rawgRepository.addGameToFavorite(data)
    }

    override suspend fun deleteFromFavorite(detailDto: GameDetailDto) {
        val data = DataMapper.gameDetailDtoToEntity(detailDto)
        rawgRepository.removeGameFromFavorite(data)
    }
}