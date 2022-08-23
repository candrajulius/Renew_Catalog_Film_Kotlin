package com.candra.katalog_film.core.ui.detail

import android.content.Context
import androidx.lifecycle.*
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.domain.model.TvShow
import com.candra.katalog_film.core.domain.usecase.MovieUseCase
import com.candra.katalog_film.core.utils.Constant.API_KEY
import com.candra.katalog_film.core.utils.Event
import com.candra.katalog_film.core.utils.FormatContent
import com.candra.katalog_film.core.utils.FormatContent.formatContentMovieRemove
import com.candra.katalog_film.core.utils.FormatContent.formatContentTvShowRemove
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel()
{
    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite get() = _isFavorite

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackbarText

    fun insertMovieToFavorite(movie: Movie, title: String, context: Context) = viewModelScope.launch {
        movieUseCase.insertToFavoriteMovie(movie)
        _snackbarText.value = Event(FormatContent.formatContentMovieAdded(context,title))
    }

    fun insertTvShowToFavorite(tvShow: TvShow, context: Context, title: String) = viewModelScope.launch {
        movieUseCase.insertToFavoriteTvShow(tvShow)
        _snackbarText.value = Event(FormatContent.formatContentTvShowAdded(context,title))
    }

    fun removeMovieFromFavorite(movie: Movie, title: String, context: Context) = viewModelScope.launch {
        movieUseCase.deleteFavoriteMovie(movie)
        _snackbarText.value = Event(title.formatContentMovieRemove(context))
    }

    fun removeTvShowFromFavorite(tvShow: TvShow,title: String,context: Context) = viewModelScope.launch {
        movieUseCase.deleteFavoriteTvShow(tvShow)
        _snackbarText.value = Event(title.formatContentTvShowRemove(context))
    }

    fun isFavoriteFromFavorite(title: String) = viewModelScope.launch {
        movieUseCase.isFavorite(title).collect {
            _isFavorite.value = it
        }
    }

    fun getGenreMovie(id: Int) = movieUseCase.getGenreMovie(id,API_KEY).asLiveData()

    fun getGenreTvShow(id: Int) = movieUseCase.getGenreTvShow(id,API_KEY).asLiveData()

    fun getVideosMovie(movieId: Int) = movieUseCase.getVideoMovie(movieId,API_KEY).asLiveData()

    fun getVideoTvShow(tvShowId: Int) = movieUseCase.getVideoTvShow(tvShowId,API_KEY).asLiveData()

    fun getRecommendationMovie(movieId: Int) = movieUseCase.getRecommendationMovie(movieId,API_KEY).asLiveData()

    fun getRecommendationTvShow(tvShowId: Int) = movieUseCase.getRecommendationTvShow(tvShowId, API_KEY).asLiveData()

}