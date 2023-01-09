package com.example.rawg.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class PlatformsItem(

    @field:SerializedName("requirements")
	val requirements: Requirements? = null,

    @field:SerializedName("released_at")
	val releasedAt: String? = null,

    @field:SerializedName("platform")
	val platform: Platform? = null
)