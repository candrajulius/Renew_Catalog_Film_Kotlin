package com.candra.katalog_film.core.domain.model

import android.os.Parcelable
import com.candra.katalog_film.core.data.source.remote.response.peopleresponse.Cast
import kotlinx.parcelize.Parcelize

@Parcelize
data class Casting(
    val id: Int,
    val name: String,
    val character: String,
    val popularity: Double,
    val profilePath: String,
    val gender: Int,
    val isFavorite: Boolean
): Parcelable

fun List<Cast>.toGenereteCasting(): MutableList<Casting>{
    val listResponseCasting = mutableListOf<Casting>()
    this.forEach { listResponseCasting.add(it.toCasting()) }
    return listResponseCasting
}

fun Cast.toCasting(): Casting = Casting(
    id = this.id ?: 0,
    name = this.name ?: "Data is null",
    character = this.character ?: "Data is null",
    popularity = this.popularity ?: 0.0,
    profilePath = this.profilePath ?: "Data is null",
    gender = this.gender ?: 0,
    isFavorite = false
)