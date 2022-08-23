package com.candra.katalog_film.core.domain.model

import com.candra.katalog_film.core.data.source.remote.response.trailerresponse.Result

data class Trailer(
    val id: String,
    val name: String,
    val type: String,
    val key: String
)

fun List<Result>.toGenerateTrailer(): MutableList<Trailer>{
    val listTrailerResponse = mutableListOf<Trailer>()
    this.forEach { listTrailerResponse.add(it.toTrailer()) }
    return listTrailerResponse
}

fun Result.toTrailer(): Trailer = Trailer(
    id = this.id ?: "0",
    name = this.name ?: "Data is null",
    type = this.type ?: "Data is null",
    key = this.key ?: "Data is null"
)