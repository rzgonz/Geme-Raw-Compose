package com.example.rawg.data.remote

import com.example.rawg.data.remote.response.detail.GameDetailResponse
import com.example.rawg.data.remote.response.listgame.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RawgWebServices {

    @GET("games")
    suspend fun getListGame(
        @QueryMap params: Map<String, String>
    ): ListGameResponse

    @GET("games/{gameId}")
    suspend fun getDetail(
        @Path("gameId")gameId:String
    ): GameDetailResponse
}