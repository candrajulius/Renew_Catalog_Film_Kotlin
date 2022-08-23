package com.candra.katalog_film.core.domain.usecase

import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MovieUseCase{
    fun getNowPlayingMovie(apiKey: String,page: Int): Flow<States<List<Movie>>>

    fun getTvShowPlayingNow(apiKey: String,page: Int): Flow<States<List<TvShow>>>

    fun getTrendingMovieAndTvShow(mediaType: String,timeWindow: String,apiKey: String)
            : Flow<States<List<Movie>>>

    fun getTrendingTvShow(mediaType: String,timeWindow: String,apiKey: String)
            : Flow<States<List<TvShow>>>

    fun getPopularNowMovie(apiKey: String,page: Int): Flow<States<List<Movie>>>

    fun getPopularNowTvShow(apiKey: String,page: Int): Flow<States<List<TvShow>>>

    fun searchMovie(apiKey: String,query: String): Flow<States<List<Movie>>>

    fun getAllFavoriteMovie(): Flow<List<Movie>>

    fun getAllFavoriteTvShow(): Flow<List<TvShow>>

    suspend fun insertToFavoriteMovie(movie: Movie)

    suspend fun insertToFavoriteTvShow(tvShow: TvShow)

    suspend fun deleteFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteTvShow(tvShow: TvShow)

    fun isFavorite(title: String): Flow<Boolean>

    fun getGenreMovie(id: Int,apiKey: String): Flow<States<List<Genres>>>

    fun getGenreTvShow(id: Int,apiKey: String): Flow<States<List<Genres>>>

    fun getCrewTvShow(tvId: Int,apiKey: String): Flow<States<List<Crew>>>

    fun getCastingTvShow(tvId: Int,apiKey: String): Flow<States<List<Casting>>>

    fun getCrewMovie(movieId: Int,apiKey: String): Flow<States<List<Crew>>>

    fun getCastingMovie(movieId: Int,apiKey: String): Flow<States<List<Casting>>>

    fun getVideoMovie(movieId: Int,apiKey: String): Flow<States<List<Trailer>>>

    fun getVideoTvShow(tvShow: Int,apiKey: String): Flow<States<List<Trailer>>>

    fun getDetailPeopleById(peopleId: Int,apiKey: String): Flow<States<DetailPeopleModel>>

    fun getRecommendationTvShow(tvShowId: Int,apiKey: String): Flow<States<List<TvShow>>>

    fun getRecommendationMovie(movieId: Int,apiKey: String): Flow<States<List<Movie>>>

    fun getTopRated(apiKey: String,page: Int): Flow<States<List<Movie>>>

    fun getTopRatedTvShow(apiKey: String,page: Int): Flow<States<List<TvShow>>>

    fun searchTvShow(query: String): Flow<States<List<TvShow>>>

}