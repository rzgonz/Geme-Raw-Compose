package com.example.rawg.data.remote.response.listgame

import com.google.gson.annotations.SerializedName

data class Filters(

	@field:SerializedName("years")
	val years: List<YearsItem?>? = null
)