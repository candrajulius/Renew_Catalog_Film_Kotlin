package com.candra.katalog_film.core.domain.repository

import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun getNowPlayingMovie(apiKey: String,page: Int): Flow<States<List<Movie>>>

    fun getTvShowPlayingNow(apiKey: String,page: Int): Flow<States<List<TvShow>>>

    fun getTrendingMovieAndTvShow(mediaType: String,timeWindow: String,apiKey: String):
            Flow<States<List<Movie>>>

    fun getPopularMovie(apiKey: String,page: Int): Flow<States<List<Movie>>>

    fun getTrendingTvShow(mediaType: String,timeWindow: String,apiKey: String)
            : Flow<States<List<TvShow>>>

    fun getPopularTvShow(apiKey: String,page: Int): Flow<States<List<TvShow>>>

    fun searchMovie(apiKey: String,query: String): Flow<States<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun getFavoriteTvShow(): Flow<List<TvShow>>

    suspend fun insertToFavoriteMovie(movie: Movie)

    suspend fun insertToFavoriteTvShow(tvShow: TvShow)

    suspend fun deleteFromFavoriteMovie(movie: Movie)

    suspend fun deleteFromFavoriteTVShow(tvShow: TvShow)

    fun isFavorite(title: String): Flow<Boolean>

    fun getGenreMovie(id: Int,apiKey: String): Flow<States<List<Genres>>>

    fun getGenreTvShow(id: Int,apiKey: String): Flow<States<List<Genres>>>

    fun getCrewTvShow(tvShowId: Int,apiKey: String): Flow<States<List<Crew>>>

    fun getCastingTvShow(tvShowId: Int,apiKey: String): Flow<States<List<Casting>>>

    fun getCrewMovie(movieId: Int,apiKey: String): Flow<States<List<Crew>>>

    fun getCastingMovie(movieId: Int,apiKey: String): Flow<States<List<Casting>>>

    fun getVidoeMovie(moveId: Int,apiKey: String): Flow<States<List<Trailer>>>

    fun getVideoTvShow(tvShowId: Int,apiKey: String): Flow<States<List<Trailer>>>

    fun getDetailPeopleById(peopleId: Int,apiKey: String): Flow<States<DetailPeopleModel>>

    fun getRecommendationMovie(movieId: Int,apiKey: String):Flow<States<List<Movie>>>

    fun getRecommendationTvShow(tvShowId: Int,apiKey: String): Flow<States<List<TvShow>>>

    fun getTopRated(apiKey: String,page: Int): Flow<States<List<Movie>>>

    fun getTopRatedTvShow(apiKey: String,page: Int): Flow<States<List<TvShow>>>

    fun searchTvShow(query: String): Flow<States<List<TvShow>>>

}