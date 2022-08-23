package com.candra.katalog_film.core.data.source.remote.response.tv_show_response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultTvShows: List<ResultTvShow>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)