package com.example.rawg.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class StoresItem(

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("store")
	val store: Store? = null,

    @field:SerializedName("url")
	val url: String? = null
)