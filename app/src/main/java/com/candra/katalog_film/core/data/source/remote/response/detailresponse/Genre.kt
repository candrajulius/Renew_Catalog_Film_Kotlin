package com.candra.katalog_film.core.data.source.remote.response.detailresponse


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)