package com.candra.katalog_film.core.data.source.remote

import android.util.Log
import com.candra.katalog_film.core.data.source.remote.network.ApiService
import com.candra.katalog_film.core.domain.model.*
import com.candra.katalog_film.core.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    companion object {
        const val TAG = "RemoteDataSource"
    }

    fun getPlayingNow(apiKey: String, page: Int) = flow<States<List<Movie>>> {
        emit(States.loading())
        val dataService = apiService.getNowPlayingMovie(apiKey, page)
        dataService.let {
            if (it.isSuccessful && it.body() != null) {
                emit(States.success(it.body()?.results?.toGenerateListMovie() ?: listOf()))
            } else {
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "getPlayingNow: ${it.message}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTvShowPlayingNow(apiKey: String, page: Int) = flow<States<List<TvShow>>> {
        emit(States.loading())
        apiService.getTvShowPlayingNow(apiKey, page = page).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.resultTvShows?.toGenerateTvShow() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getTvShowPlayingNow: ${it.message.toString()}")
    }.flowOn(Dispatchers.IO)

    fun getPopularMovie(apiKey: String, page: Int) = flow<States<List<Movie>>> {
        emit(States.loading())
        apiService.getPopularMovie(apiKey, page).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.results?.toGenerateListMovie() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getPopularMovie: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getPopularTvShow(apiKey: String, page: Int) = flow<States<List<TvShow>>> {
        emit(States.loading())
        apiService.getPopularTvShow(apiKey, page).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.resultTvShows?.toGenerateTvShow() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getPopularTvShow: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTrendingMovieAndTvShow(mediaType: String, timeWindow: String, apiKey: String) =
        flow<States<List<Movie>>> {
            emit(States.loading())
            apiService.getTrendingMovieAndTvShow(mediaType, timeWindow, apiKey).let {
                if (it.isSuccessful && it.body() != null) emit(
                    States.success(
                        it.body()?.results?.toGenerateListMovie() ?: listOf()
                    )
                )
                else emit(States.failed(it.message().toString()))
            }
        }.catch {
            Log.d(TAG, "getTrendingMovieAndTvShow: ${it.message.toString()}")
            emit(States.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun getTrendingTvShow(mediaType: String, timeWindow: String, apiKey: String) =
        flow<States<List<TvShow>>> {
            emit(States.loading())
            apiService.getTrendingTvShow(mediaType, timeWindow, apiKey).let {
                if (it.isSuccessful && it.body() != null) {
                    emit(States.success(it.body()?.resultTvShows?.toGenerateTvShow() ?: listOf()))
                } else {
                    emit(States.failed(it.message().toString()))
                }
            }
        }.catch {
            Log.d(TAG, "getTrendingTvShow: ${it.message.toString()}")
            emit(States.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun searchMovie(apiKey: String, query: String) = flow<States<List<Movie>>> {
        emit(States.loading())
        apiService.searchMovie(apiKey = apiKey, query = query).let {
            if (it.isSuccessful && it.body() != null) {
                val movieData = it.body()?.results?.toGenerateListRecomendationMovie()
                if (movieData?.isEmpty() == true) {
                    emit(States.empty())
                } else {
                    emit(States.success(movieData ?: listOf()))
                }
            } else {
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "searchMovie: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    // Genre
    fun genreMovies(id: Int, apiKey: String) = flow<States<List<Genres>>> {
        emit(States.loading())
        apiService.getDetailMovie(id, apiKey).let {
            if (it.isSuccessful && it.body() != null) {
                val genre = it.body()?.genres?.toGenereteGenre()
                emit(States.success(genre ?: listOf()))
            } else {
                Log.d(TAG, "genreMovies: ${it.message()}")
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "genreMovies: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun genreTvShow(id: Int, apiKey: String) = flow<States<List<Genres>>> {
        emit(States.loading())
        apiService.getDetailTvShow(id, apiKey).let {
            if (it.isSuccessful && it.body() != null) {
                val genresTvShow = it.body()?.genres?.toGenereteGenre()
                emit(States.success(genresTvShow ?: listOf()))
            } else {
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "genreTvShow: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCrewCreditTvShow(tvId: Int, apiKey: String) = flow<States<List<Crew>>> {
        emit(States.loading())
        apiService.getCreditsTvShow(tvId, apiKey).let {
            if (it.isSuccessful && it.body() != null) {
                val crewTvShow = it.body()?.crew?.toGenereteCrew()
                emit(States.success(crewTvShow ?: listOf()))
            } else {
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "getCrewCreditTvShow: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCastingTvShow(tvId: Int, apiKey: String) = flow<States<List<Casting>>> {
        emit(States.loading())
        apiService.getCreditsTvShow(tvId, apiKey).let {
            if (it.isSuccessful && it.body() != null) {
                val castingTvShow = it.body()?.cast?.toGenereteCasting()
                emit(States.success(castingTvShow ?: listOf()))
            } else {
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "getCastingTvShow: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCrewMovie(movieId: Int, apiKey: String) = flow<States<List<Crew>>> {
        emit(States.loading())
        apiService.getCreditsMovie(movieId, apiKey).let {
            if (it.isSuccessful) emit(States.success(it.body()?.crew?.toGenereteCrew() ?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getCrewMovie: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCastingMovie(movieId: Int, apiKey: String) = flow<States<List<Casting>>> {
        emit(States.loading())
        apiService.getCreditsMovie(movieId, apiKey).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.cast?.toGenereteCasting() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getCastingMovie: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getVideoMovie(movieId: Int, apiKey: String) = flow<States<List<Trailer>>> {
        emit(States.loading())
        apiService.getVideoMovie(movieId, apiKey).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.results?.toGenerateTrailer() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getVideos: ${it.message.toString()}")
    }.flowOn(Dispatchers.IO)

    fun getVideoTvShow(idTvShow: Int, apiKey: String) = flow<States<List<Trailer>>> {
        emit(States.loading())
        apiService.getTvShowVideo(idTvShow, apiKey).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.results?.toGenerateTrailer() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getVideoTvShow: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getDetailPeople(peopleId: Int, apiKey: String) = flow<States<DetailPeopleModel>> {
        emit(States.loading())
        apiService.getPeopleById(peopleId, apiKey).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()!!.toDetailPeopleModel()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getDetailPeople: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getRecommendationMovie(recommendationMovieId: Int, apiKey: String) =
        flow<States<List<Movie>>> {
            emit(States.loading())
            apiService.getRecommendationMovie(recommendationMovieId, apiKey).let {
                if (it.isSuccessful && it.body() != null) emit(
                    States.success(
                        it.body()?.results?.toGenerateListRecomendationMovie() ?: listOf()
                    )
                )
                else emit(States.failed(it.message().toString()))
            }
        }.catch {
            Log.d(TAG, "getRecommendationMovie: ${it.message.toString()}")
            emit(States.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun getRecommendationTvShow(recommendationTvShow: Int, apiKey: String) =
        flow<States<List<TvShow>>> {
            emit(States.loading())
            apiService.getRecommendationTvShow(recommendationTvShow, apiKey).let {
                if (it.isSuccessful && it.body() != null) emit(
                    States.success(
                        it.body()?.results?.toGenerateRecomendationTvShow() ?: listOf()
                    )
                )
                else emit(States.failed(it.message().toString()))
            }
        }.catch {
            Log.d(TAG, "getRecommendationTvShow: ${it.message.toString()}")
            emit(States.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun getTopRated(apiKey: String, page: Int) = flow<States<List<Movie>>> {
        emit(States.loading())
        apiService.getTopRated(apiKey, page = page).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.results?.toGenerateListMovie() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getTopRated: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTopRatedTvShow(apiKey: String, page: Int) = flow<States<List<TvShow>>> {
        emit(States.loading())
        apiService.getTopRatedTvShow(apiKey, page = page).let {
            if (it.isSuccessful && it.body() != null) emit(
                States.success(
                    it.body()?.resultTvShows?.toGenerateTvShow() ?: listOf()
                )
            )
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getTopRatedTvShow: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun searchTvShow(query: String) = flow<States<List<TvShow>>> {
        emit(States.loading())
        apiService.searchTvShow(apiKey = Constant.API_KEY, query).let {
            if (it.isSuccessful && it.body() != null) {
                val tvShowSearch = it.body()?.results?.toGenerateRecomendationTvShow()
                if (tvShowSearch?.isEmpty() == true) {
                    emit(States.empty())
                } else {
                    emit(States.success(tvShowSearch ?: listOf()))
                }
            } else {
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "searchTvShow: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}