package com.example.rawg.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class Requirements(

	@field:SerializedName("minimum")
	val minimum: String? = null,

	@field:SerializedName("recommended")
	val recommended: String? = null
)