package com.candra.katalog_film.core.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel()
{
    fun getAllFavoriteMovie() = movieUseCase.getAllFavoriteMovie().asLiveData()
    fun getAllFavoriteTvShow() = movieUseCase.getAllFavoriteTvShow().asLiveData()
}