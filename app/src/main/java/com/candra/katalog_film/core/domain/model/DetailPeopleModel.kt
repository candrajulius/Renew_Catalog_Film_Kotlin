package com.candra.katalog_film.core.domain.model

import com.candra.katalog_film.core.data.source.remote.response.peopleresponse.detailpeopleresponse.DetailPeopleResponse

data class DetailPeopleModel(
    val id: Int,
    val gender: Int,
    val birthDay: String,
    val name: String,
    val popularity: Double,
    val profilePath: String,
    val biografi: String,
    val location: String
)

fun DetailPeopleResponse.toDetailPeopleModel(): DetailPeopleModel = DetailPeopleModel(
    id = this.id ?: 0,
    gender = this.gender ?: 0,
    birthDay = this.birthday,
    name = this.name ?:  "Data is null",
    popularity = this.popularity ?: 0.0,
    profilePath = this.profilePath ?: "Data is null",
    biografi = this.biography ?: "Data is null",
    location = this.placeOfBirth ?: "Data is null"
)