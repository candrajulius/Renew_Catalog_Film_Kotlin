package com.candra.katalog_film.core.data.source.remote.response.trailerresponse


import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)