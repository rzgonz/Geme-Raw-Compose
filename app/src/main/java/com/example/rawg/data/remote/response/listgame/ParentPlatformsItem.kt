package com.example.rawg.data.remote.response.listgame

import com.google.gson.annotations.SerializedName

data class ParentPlatformsItem(

	@field:SerializedName("platform")
	val platform: Platform? = null
)