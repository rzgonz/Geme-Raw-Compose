package com.example.rawg.domain

import com.common.common.ext.orZero
import com.example.rawg.data.local.entity.GameDetail
import com.example.rawg.data.remote.response.detail.GameDetailResponse
import com.example.rawg.data.remote.response.listgame.ListGameResponse
import com.example.rawg.data.remote.response.listgame.ResultsItem
import com.example.rawg.domain.dto.GameDetailDto
import com.example.rawg.domain.dto.GameDto
import com.example.rawg.domain.dto.ListGameDto

object DataMapper {
    fun listGameResponseToDto(
        respose: ListGameResponse
    ): ListGameDto = ListGameDto(
        respose.results?.map {
            gameResponseToDto(it)
        }.orEmpty()
    )

    private fun gameResponseToDto(response: ResultsItem?): GameDto =
        GameDto(
            id = response?.id.orZero(),
            name = response?.name.orEmpty(),
            backgroundImage = response?.backgroundImage.orEmpty(),
            rating = response?.rating ?: 0.0,
            released = response?.released.orEmpty()
        )

    fun gameDetailResponseToDto(respose: GameDetailResponse?): GameDetailDto =
        GameDetailDto(
            gameId = respose?.id.orZero(),
            imagePreview = respose?.backgroundImage.orEmpty(),
            publishers = respose?.publishers?.joinToString(separator = ", ") { it?.name.orEmpty() }
                .orEmpty(),
            name = respose?.name.orEmpty(),
            releaseDate = respose?.released.orEmpty(),
            rating = respose?.rating.orZero(),
            players = respose?.added.orZero(),
            description = respose?.description.orEmpty()
        )

    fun gameDetailDtoToEntity(dto: GameDetailDto): GameDetail =
        GameDetail(
            gameId = dto.gameId,
            imagePreview = dto.imagePreview,
            name = dto.name,
            releaseDate = dto.releaseDate,
            rating = dto.rating
        )

    fun gameDetailToGameDetailDto(entity: GameDetail) = GameDetailDto(
        gameId = entity.gameId,
        name = entity.name,
        imagePreview = entity.imagePreview,
        rating = entity.rating,
        releaseDate = entity.releaseDate
    )
}