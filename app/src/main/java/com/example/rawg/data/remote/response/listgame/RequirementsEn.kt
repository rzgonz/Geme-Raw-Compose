package com.example.rawg.data.remote.response.listgame

import com.google.gson.annotations.SerializedName

data class RequirementsEn(

	@field:SerializedName("minimum")
	val minimum: String? = null,

	@field:SerializedName("recommended")
	val recommended: String? = null
)