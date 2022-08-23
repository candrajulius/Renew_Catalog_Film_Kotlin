package com.candra.katalog_film.core.domain.model

import com.candra.katalog_film.core.data.source.remote.response.detailresponse.Genre

data class Genres(
    val id: Int,
    val name: String
)

fun List<Genre>.toGenereteGenre(): MutableList<Genres>{
    val listResponseGenres = mutableListOf<Genres>()
    this.forEach { listResponseGenres.add(it.toGenres()) }
    return listResponseGenres
}

fun Genre.toGenres(): Genres = Genres(
    id = this.id ?: 0,
    name = this.name ?: "Data is null"
)