package com.example.rawg.domain.dto

data class GameDto(
    val id:Int = 0,
    val name: String = "",
    val backgroundImage: String = "",
    val released: String = "",
    val rating: Double = 0.0,
)
