package com.candra.katalog_film.core.data.source.remote.response.peopleresponse


import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)