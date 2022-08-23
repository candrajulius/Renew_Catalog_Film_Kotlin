package com.candra.katalog_film.core.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import com.candra.katalog_film.core.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel()
{
    fun searchMovie(query: String) = movieUseCase.searchMovie(Constant.API_KEY,query).asLiveData()

    fun searchTvShow(query: String) = movieUseCase.searchTvShow(query).asLiveData()
}