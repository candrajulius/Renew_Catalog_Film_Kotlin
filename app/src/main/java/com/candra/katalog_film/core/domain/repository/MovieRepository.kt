package com.candra.katalog_film.core.domain.repository

import com.candra.katalog_film.core.data.source.local.LocalDataSource
import com.candra.katalog_film.core.data.source.remote.RemoteDataSource
import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.*
import com.candra.katalog_film.core.utils.Helper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IRepository {
    override  fun getNowPlayingMovie(apiKey: String,page: Int): Flow<States<List<Movie>>> =
        remoteDataSource.getPlayingNow(apiKey, page = page)

    override  fun getTvShowPlayingNow(apiKey: String,page: Int): Flow<States<List<TvShow>>> =
        remoteDataSource.getTvShowPlayingNow(apiKey,page)

    override fun getTrendingMovieAndTvShow(
        mediaType: String,
        timeWindow: String,
        apiKey: String
    ): Flow<States<List<Movie>>> =
        remoteDataSource.getTrendingMovieAndTvShow(mediaType,timeWindow,apiKey)

    override  fun getPopularMovie(apiKey: String,page: Int): Flow<States<List<Movie>>> =
        remoteDataSource.getPopularMovie(apiKey,page)

    override fun getTrendingTvShow(
        mediaType: String,
        timeWindow: String,
        apiKey: String
    ): Flow<States<List<TvShow>>> =
        remoteDataSource.getTrendingTvShow(mediaType,timeWindow,apiKey)


    override  fun getPopularTvShow(apiKey: String,page: Int): Flow<States<List<TvShow>>> =
        remoteDataSource.getPopularTvShow(apiKey,page)

    override  fun searchMovie(apiKey: String, query: String): Flow<States<List<Movie>>> =
        remoteDataSource.searchMovie(apiKey,query)

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getAllFavoriteAny().map {
            Helper.mapEntitiesToDomainMovie(it)
        }

    override fun getFavoriteTvShow(): Flow<List<TvShow>>  =
        localDataSource.getAllFavoriteAny().map {
            Helper.mapEntitiesToDomainTvShow(it)
        }

    override suspend fun insertToFavoriteMovie(movie: Movie) {
        localDataSource.insertToFavoriteEntity(
            Helper.mapDomainToEntityMovie(movie)
        )
    }

    override suspend fun insertToFavoriteTvShow(tvShow: TvShow) {
        localDataSource.insertToFavoriteEntity(
            Helper.mapDomainToEntityTvShow(tvShow)
        )
    }

    override suspend fun deleteFromFavoriteMovie(movie: Movie) {
        localDataSource.deleteFromFavorite(
            Helper.mapDomainToEntityMovie(movie)
        )
    }

    override suspend fun deleteFromFavoriteTVShow(tvShow: TvShow) {
        localDataSource.deleteFromFavorite(
            Helper.mapDomainToEntityTvShow(tvShow)
        )
    }

    override fun isFavorite(title: String): Flow<Boolean> =
        localDataSource.isFavorite(title)

    override fun getGenreMovie(id: Int, apiKey: String): Flow<States<List<Genres>>> {
       return remoteDataSource.genreMovies(id,apiKey)
    }

    override fun getGenreTvShow(id: Int, apiKey: String): Flow<States<List<Genres>>> {
        return remoteDataSource.genreTvShow(id,apiKey)
    }

    override fun getCrewTvShow(tvShowId: Int, apiKey: String): Flow<States<List<Crew>>> {
       return remoteDataSource.getCrewCreditTvShow(tvShowId,apiKey)
    }

    override fun getCastingTvShow(tvShowId: Int, apiKey: String): Flow<States<List<Casting>>> {
       return remoteDataSource.getCastingTvShow(tvShowId,apiKey)
    }

    override fun getCrewMovie(movieId: Int, apiKey: String): Flow<States<List<Crew>>> {
        return remoteDataSource.getCrewMovie(movieId,apiKey)
    }

    override fun getCastingMovie(movieId: Int, apiKey: String): Flow<States<List<Casting>>> {
       return remoteDataSource.getCastingMovie(movieId,apiKey)
    }

    override fun getVidoeMovie(moveId: Int, apiKey: String): Flow<States<List<Trailer>>> {
       return remoteDataSource.getVideoMovie(moveId,apiKey)
    }

    override fun getVideoTvShow(tvShowId: Int, apiKey: String): Flow<States<List<Trailer>>> {
        return remoteDataSource.getVideoTvShow(tvShowId,apiKey)
    }

    override fun getDetailPeopleById(
        peopleId: Int,
        apiKey: String
    ): Flow<States<DetailPeopleModel>> {
        return remoteDataSource.getDetailPeople(peopleId,apiKey)
    }

    override fun getRecommendationMovie(movieId: Int, apiKey: String): Flow<States<List<Movie>>> {
        return remoteDataSource.getRecommendationMovie(movieId,apiKey)
    }

    override fun getRecommendationTvShow(tvShowId: Int, apiKey: String): Flow<States<List<TvShow>>> {
        return remoteDataSource.getRecommendationTvShow(tvShowId,apiKey)
    }

    override fun getTopRated(apiKey: String, page: Int): Flow<States<List<Movie>>> {
        return remoteDataSource.getTopRated(apiKey,page)
    }

    override fun getTopRatedTvShow(apiKey: String, page: Int): Flow<States<List<TvShow>>> {
        return remoteDataSource.getTopRatedTvShow(apiKey,page)
    }

    override fun searchTvShow(query: String): Flow<States<List<TvShow>>> {
        return remoteDataSource.searchTvShow(query)
    }

}