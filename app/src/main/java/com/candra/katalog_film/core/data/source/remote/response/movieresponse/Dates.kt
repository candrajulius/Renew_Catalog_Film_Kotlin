package com.candra.katalog_film.core.data.source.remote.response.movieresponse

import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("maximum")
    val maximum: String,
    @SerializedName("minimum")
    val minimum: String
)