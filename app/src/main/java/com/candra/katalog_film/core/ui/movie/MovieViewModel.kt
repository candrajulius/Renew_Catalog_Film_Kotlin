package com.candra.katalog_film.core.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Constant.API_KEY
import com.candra.katalog_film.core.utils.Constant.MEDIA_TYPE_MOVIE
import com.candra.katalog_film.core.utils.Constant.WEEK
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    fun moviePlayingNow(page: Int) = movieUseCase.getNowPlayingMovie(API_KEY,page).asLiveData()

    fun movieTrendingDay(trending: String) = movieUseCase.getTrendingMovieAndTvShow(MEDIA_TYPE_MOVIE,trending, API_KEY).asLiveData()

    fun movieRecomended(page: Int) = movieUseCase.getPopularNowMovie(API_KEY,page).asLiveData()

    fun movieTopRated(page: Int) = movieUseCase.getTopRated(Constant.API_KEY,page).asLiveData()

}