package com.example.rawg.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class MetacriticPlatformsItem(

	@field:SerializedName("metascore")
	val metascore: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("platform")
	val platform: Platform? = null
)