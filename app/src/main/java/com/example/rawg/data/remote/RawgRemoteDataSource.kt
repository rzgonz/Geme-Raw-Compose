package com.example.rawg.data.remote

import com.example.rawg.data.remote.response.detail.GameDetailResponse
import com.example.rawg.data.remote.response.listgame.ListGameResponse


class RawgRemoteDataSource(
    private val rawgWebServices: RawgWebServices
) : RawgWebServices {


    override suspend fun getListGame(params: Map<String, String>): ListGameResponse =
        rawgWebServices.getListGame(params)

    override suspend fun getDetail(gameId: String): GameDetailResponse =
        rawgWebServices.getDetail(gameId = gameId)


    private fun addApiKey(params: Map<String, String> = mapOf()):Map<String,String>{
        val map = mapOf(API_KEY to API_KEY_VALUE)
        return  map+params
    }

    companion object{
        private const val API_KEY = "key"
        private const val API_KEY_VALUE = "576c6dcb71534553893309681569b92f"

    }
}