package com.example.rawg.data.remote.response.listgame

import com.google.gson.annotations.SerializedName

data class ListGameResponse(

    @field:SerializedName("next")
	val next: String? = null,

    @field:SerializedName("nofollow")
	val nofollow: Boolean? = null,

    @field:SerializedName("noindex")
	val noindex: Boolean? = null,

    @field:SerializedName("nofollow_collections")
	val nofollowCollections: List<String?>? = null,

    @field:SerializedName("previous")
	val previous: String? = null,

    @field:SerializedName("count")
	val count: Int? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("seo_h1")
	val seoH1: String? = null,

    @field:SerializedName("filters")
	val filters: Filters? = null,

    @field:SerializedName("seo_title")
	val seoTitle: String? = null,

    @field:SerializedName("seo_description")
	val seoDescription: String? = null,

    @field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

    @field:SerializedName("seo_keywords")
	val seoKeywords: String? = null
)