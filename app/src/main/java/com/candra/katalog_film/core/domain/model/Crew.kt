package com.candra.katalog_film.core.domain.model

import android.os.Parcelable
import com.candra.katalog_film.core.data.source.remote.response.peopleresponse.Crew
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crew(
    val id: Int,
    val name: String,
    val job: String,
    val gender: Int,
    val profilePath: String,
    val poupularity: Double,
    val isFavorite: Boolean
): Parcelable

fun List<Crew>.toGenereteCrew(): MutableList<com.candra.katalog_film.core.domain.model.Crew>{
    val listReponseCrews = mutableListOf<com.candra.katalog_film.core.domain.model.Crew>()
    this.forEach { listReponseCrews.add(it.toCrew()) }
    return listReponseCrews
}

fun Crew.toCrew(): com.candra.katalog_film.core.domain.model.Crew = com.candra.katalog_film.core.domain.model.Crew(
    id = this.id ?: 0,
    name = this.name ?: "Data is null",
    job = this.job ?: "Data is null",
    gender = this.gender ?: 0,
    profilePath = this.profilePath ?: "Data is null",
    poupularity = this.popularity ?: 0.0,
    isFavorite = false
)