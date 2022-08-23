package com.candra.katalog_film.core.domain.model

import android.os.Parcelable
import com.candra.katalog_film.core.data.source.remote.response.movie_recomendation_response.Result
import com.candra.katalog_film.core.data.source.remote.response.movieresponse.ResultMovie
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val releaseData: String,
    val popularity: Double,
    val overview: String,
    val thumbnail: String,
    val cover: String,
    val isFavorite: Boolean
): Parcelable

fun List<ResultMovie>.toGenerateListMovie(): MutableList<Movie>{
    val listResponseMovie = mutableListOf<Movie>()
    this.forEach { listResponseMovie.add(it.toMovie()) }
    return listResponseMovie
}

fun ResultMovie.toMovie(): Movie = Movie(
    id = this.id,
    title = this.title,
    voteAverage = this.voteAverage,
    releaseData = this.releaseDate,
    popularity = this.popularity,
    overview = this.overview,
    thumbnail = this.posterPath,
    cover = this.backdropPath,
    isFavorite = false
)

fun List<Result>.toGenerateListRecomendationMovie(): MutableList<Movie>{
    val listResponseRecomendation = mutableListOf<Movie>()
    this.forEach { listResponseRecomendation.add(it.toGenerateMovieRecommendation()) }
    return listResponseRecomendation
}

fun Result.toGenerateMovieRecommendation(): Movie = Movie(
    id = this.id ?: 0,
    title = this.title ?: "Data is null",
    voteAverage = this.voteAverage ?: 0.0,
    releaseData = this.releaseDate ?: "",
    popularity = this.popularity ?: 0.0,
    overview = this.overview ?: "Data is null",
    thumbnail = this.posterPath ?: "Data is null",
    cover = this.backdropPath ?: "Data is null",
    isFavorite = false
)