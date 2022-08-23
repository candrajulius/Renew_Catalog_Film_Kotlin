package com.candra.katalog_film.core.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import com.candra.katalog_film.core.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CastingViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel()
{
    fun getCastingMovie(id: Int) = movieUseCase.getCastingMovie(id,Constant.API_KEY).asLiveData()

    fun getCastingTvShow(id: Int) = movieUseCase.getCastingTvShow(id,Constant.API_KEY).asLiveData()
}