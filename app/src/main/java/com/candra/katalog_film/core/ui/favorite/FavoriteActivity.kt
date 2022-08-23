package com.candra.katalog_film.core.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.candra.katalog_film.R
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.domain.model.TvShow
import com.candra.katalog_film.core.ui.adapter.movieadapter.RecomendedMovieAdapter
import com.candra.katalog_film.core.ui.adapter.tvshowadapter.TvRecomendedAdapter
import com.candra.katalog_film.core.ui.detail.DetailActivity
import com.candra.katalog_film.core.utils.Constant.EXTRA_MOVIE
import com.candra.katalog_film.core.utils.Constant.EXTRA_TV
import com.candra.katalog_film.core.utils.Constant.POSITION
import com.candra.katalog_film.core.utils.Helper
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.ActivityFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity: AppCompatActivity()
{

    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private lateinit var binding: ActivityFavoriteBinding

    private val movieAdapter by lazy { RecomendedMovieAdapter(::onClickMovie) }
    private val tvShowAdapter by lazy { TvRecomendedAdapter(::onClickTvShow) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapterAll()
        setObserverAllData()
    }

    private fun setObserverAllData(){
        favoriteViewModel.getAllFavoriteMovie().observe(this,this::setDataMovie)
        favoriteViewModel.getAllFavoriteTvShow().observe(this,this::setDataTvShow)
    }

    private fun onClickMovie(movie: Movie){
        Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
            putExtra(POSITION,1)
            putExtra(EXTRA_MOVIE,movie)
        }.also { startActivity(it) }
    }

    private fun setDataTvShow(tvShow: List<TvShow>){
        if (tvShow.isEmpty()){
            showEmptyText(true)
        }else{
            showEmptyText(false)
            tvShowAdapter.temptAllDataTvShowRecomended(tvShow)
        }
    }

    private fun setAdapterAll(){
        binding.apply {
            rvFavoriteMovie.apply {
                layoutManager = Helper.constLinearLayoutManager(this@FavoriteActivity,2)
                adapter = movieAdapter
            }
            toolbarFavorite.apply {
                title = "Candra Julius Sinaga"
                subtitle = "Favorite"
            }
            toolbarFavorite.setNavigationOnClickListener {
                onBackPressed()
            }
            binding.toolbarFavorite.setNavigationIcon(
                if (isDarkMode) R.drawable.ic_baseline_close_24
                else R.drawable.ic_baseline_close_24_black
            )
        }
    }

    private fun setDataMovie(movie: List<Movie>){
        if (movie.isEmpty()){
            showEmptyText(true)
        }else{
            showEmptyText(false)
            movieAdapter.temptAllData(movie)
        }
    }

    private fun onClickTvShow(tvShow: TvShow){
        Intent(this@FavoriteActivity,DetailActivity::class.java).apply {
            putExtra(POSITION,2)
            putExtra(EXTRA_TV,tvShow)
        }.also { startActivity(it) }
    }

    private fun showEmptyText(isEmpty: Boolean){
        binding.viewEmpty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.rvFavoriteMovie.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}