package com.candra.katalog_film.core.domain.usecase

import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.*
import com.candra.katalog_film.core.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteract @Inject constructor(
    private val movieIRepository: IRepository
): MovieUseCase {
    // Network
    override  fun getNowPlayingMovie(apiKey: String,page: Int): Flow<States<List<Movie>>> =
        movieIRepository.getNowPlayingMovie(apiKey, page = page)

    override  fun getTvShowPlayingNow(apiKey: String,page: Int): Flow<States<List<TvShow>>> =
        movieIRepository.getTvShowPlayingNow(apiKey,page)

    override  fun getTrendingMovieAndTvShow(
        mediaType: String,
        timeWindow: String,
        apiKey: String
    ): Flow<States<List<Movie>>> =
        movieIRepository.getTrendingMovieAndTvShow(mediaType, timeWindow, apiKey)

    override fun getTrendingTvShow(
        mediaType: String,
        timeWindow: String,
        apiKey: String
    ): Flow<States<List<TvShow>>> =
        movieIRepository.getTrendingTvShow(mediaType,timeWindow,apiKey)

    override  fun getPopularNowMovie(apiKey: String,page: Int): Flow<States<List<Movie>>> =
        movieIRepository.getPopularMovie(apiKey,page)

    override  fun getPopularNowTvShow(apiKey: String,page: Int): Flow<States<List<TvShow>>> =
        movieIRepository.getPopularTvShow(apiKey,page)

    override  fun searchMovie(apiKey: String, query: String): Flow<States<List<Movie>>> =
        movieIRepository.searchMovie(apiKey, query)


    // Database
    override fun getAllFavoriteMovie(): Flow<List<Movie>> =
        movieIRepository.getFavoriteMovie()


    override fun getAllFavoriteTvShow(): Flow<List<TvShow>> =
        movieIRepository.getFavoriteTvShow()


    override suspend fun insertToFavoriteMovie(movie: Movie) =
        movieIRepository.insertToFavoriteMovie(movie)


    override suspend fun insertToFavoriteTvShow(tvShow: TvShow) =
        movieIRepository.insertToFavoriteTvShow(tvShow)

    override suspend fun deleteFavoriteMovie(movie: Movie) =
        movieIRepository.deleteFromFavoriteMovie(movie)

    override suspend fun deleteFavoriteTvShow(tvShow: TvShow) =
        movieIRepository.deleteFromFavoriteTVShow(tvShow)

    override fun isFavorite(title: String): Flow<Boolean> =
        movieIRepository.isFavorite(title)

    override fun getGenreMovie(id: Int, apiKey: String): Flow<States<List<Genres>>> {
        return movieIRepository.getGenreMovie(id,apiKey)
    }

    override fun getGenreTvShow(id: Int, apiKey: String): Flow<States<List<Genres>>> {
        return movieIRepository.getGenreTvShow(id,apiKey)
    }

    override fun getCrewTvShow(tvId: Int, apiKey: String): Flow<States<List<Crew>>> {
        return movieIRepository.getCrewTvShow(tvId,apiKey)
    }

    override fun getCastingTvShow(tvId: Int, apiKey: String): Flow<States<List<Casting>>> {
       return movieIRepository.getCastingTvShow(tvId,apiKey)
    }

    override fun getCrewMovie(movieId: Int, apiKey: String): Flow<States<List<Crew>>> {
        return movieIRepository.getCrewMovie(movieId,apiKey)
    }

    override fun getCastingMovie(movieId: Int, apiKey: String): Flow<States<List<Casting>>> {
       return movieIRepository.getCastingMovie(movieId,apiKey)
    }

    override fun getVideoMovie(movieId: Int, apiKey: String): Flow<States<List<Trailer>>> {
        return movieIRepository.getVidoeMovie(movieId,apiKey)
    }

    override fun getVideoTvShow(tvShowId: Int, apiKey: String): Flow<States<List<Trailer>>> {
        return movieIRepository.getVideoTvShow(tvShowId,apiKey)
    }

    override fun getDetailPeopleById(
        peopleId: Int,
        apiKey: String
    ): Flow<States<DetailPeopleModel>> {
        return movieIRepository.getDetailPeopleById(peopleId,apiKey)
    }

    override fun getRecommendationTvShow(
        tvShowId: Int,
        apiKey: String
    ): Flow<States<List<TvShow>>> {
        return movieIRepository.getRecommendationTvShow(tvShowId,apiKey)
    }

    override fun getRecommendationMovie(movieId: Int, apiKey: String): Flow<States<List<Movie>>> {
        return movieIRepository.getRecommendationMovie(movieId,apiKey)
    }

    override fun getTopRated(apiKey: String, page: Int): Flow<States<List<Movie>>> {
        return movieIRepository.getTopRated(apiKey,page)
    }

    override fun getTopRatedTvShow(apiKey: String, page: Int): Flow<States<List<TvShow>>> {
        return movieIRepository.getTopRatedTvShow(apiKey, page)
    }

    override fun searchTvShow(query: String): Flow<States<List<TvShow>>> {
        return movieIRepository.searchTvShow(query)
    }


}