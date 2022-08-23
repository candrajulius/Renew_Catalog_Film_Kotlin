package com.candra.katalog_film.core.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import com.candra.katalog_film.core.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CrewViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel()
{
    fun getCrewMovie(id: Int) = movieUseCase.getCrewMovie(id,Constant.API_KEY).asLiveData()

    fun getCrewTvShow(id: Int) = movieUseCase.getCrewTvShow(id,Constant.API_KEY).asLiveData()
}