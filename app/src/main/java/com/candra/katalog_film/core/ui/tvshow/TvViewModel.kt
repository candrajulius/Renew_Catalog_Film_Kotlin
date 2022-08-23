package com.candra.katalog_film.core.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import com.candra.katalog_film.core.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel()
{
    fun tvPlayingNow(page: Int) = movieUseCase.getTvShowPlayingNow(Constant.API_KEY,page).asLiveData()

    fun tvRecomended(page: Int) = movieUseCase.getPopularNowTvShow(Constant.API_KEY,page).asLiveData()

    fun tvTrendingWeek(trending: String) = movieUseCase.getTrendingTvShow(
        Constant.MEDIA_TYPE_TV,trending,Constant.API_KEY
    ).asLiveData()

    fun tvTopRated(page: Int) = movieUseCase.getTopRatedTvShow(Constant.API_KEY,page).asLiveData()
}