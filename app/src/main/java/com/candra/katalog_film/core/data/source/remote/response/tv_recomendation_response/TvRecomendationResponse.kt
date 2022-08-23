package com.candra.katalog_film.core.data.source.remote.response.tv_recomendation_response


import com.google.gson.annotations.SerializedName

data class TvRecomendationResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)