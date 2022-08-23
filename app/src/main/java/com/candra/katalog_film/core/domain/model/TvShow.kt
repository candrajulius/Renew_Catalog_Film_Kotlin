package com.candra.katalog_film.core.domain.model

import android.os.Parcelable
import com.candra.katalog_film.core.data.source.remote.response.tv_recomendation_response.Result
import com.candra.katalog_film.core.data.source.remote.response.tv_show_response.ResultTvShow
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String,
    val popularity: Double,
    val overview: String,
    val thumbnail: String,
    val cover: String,
    val isFavorite: Boolean
): Parcelable

fun List<ResultTvShow>.toGenerateTvShow(): MutableList<TvShow>{
    val listResponseTvShow = mutableListOf<TvShow>()
    this.forEach { listResponseTvShow.add(it.toTvShow()) }
    return listResponseTvShow
}

fun ResultTvShow.toTvShow(): TvShow = TvShow(
    id = this.id ?: 0,
    title = this.name ?: "Data is null",
    voteAverage = this.voteAverage?: 0.0,
    releaseDate = this.firstAirDate?: "Data is null",
    popularity = this.popularity ?: 0.0,
    overview = this.overview?: "Data is null",
    thumbnail = this.posterPath?: "Data is null",
    cover = this.backdropPath ?:"Data is null",
    isFavorite = false
)

fun List<Result>.toGenerateRecomendationTvShow(): MutableList<TvShow>{
    val listReponseTvShow = mutableListOf<TvShow>()
    this.forEach { listReponseTvShow.add(it.toRecommendationTvShow())}
    return listReponseTvShow
}

fun Result.toRecommendationTvShow(): TvShow = TvShow(
    id = this.id ?: 0,
    title = this.name ?: "Data is null",
    voteAverage = this.voteAverage?: 0.0,
    releaseDate = this.firstAirDate?: "",
    popularity = this.popularity ?: 0.0,
    overview = this.overview?: "Data is null",
    thumbnail = this.posterPath?: "Data is null",
    cover = this.backdropPath ?:"Data is null",
    isFavorite = false
)
