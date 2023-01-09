package com.example.rawg.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameDetail(
    @PrimaryKey val gameId:Int,
    @ColumnInfo(name = "image_preview")  val imagePreview: String,
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "releaseDate")  val releaseDate: String,
    @ColumnInfo(name = "rating")  val rating: Double,
)