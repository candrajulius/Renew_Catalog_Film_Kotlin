package com.candra.katalog_film.core.ui.people.detailpeople

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPeopleViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel()
{
    fun getDetailPeopleById(peopleId: Int) = movieUseCase.getDetailPeopleById(peopleId,Constant.API_KEY).asLiveData()

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite get() = _isFavorite

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText get() = _snackbarText




}