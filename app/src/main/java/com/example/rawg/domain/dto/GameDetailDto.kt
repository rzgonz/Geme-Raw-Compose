package com.example.rawg.domain.dto

class GameDetailDto(
    val gameId: Int = 0,
    val imagePreview: String = "",
    val publishers: String = "",
    val name: String = "",
    val releaseDate: String = "",
    val rating: Double = 0.0,
    val players: Int = 0,
    val description: String = ""
) {
}