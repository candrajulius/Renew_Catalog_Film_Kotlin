package com.candra.katalog_film.core.ui.tempt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.candra.katalog_film.R
import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.domain.model.TvShow
import com.candra.katalog_film.core.ui.adapter.movieadapter.MovieAdapter
import com.candra.katalog_film.core.ui.adapter.tvshowadapter.TvShowAdapter
import com.candra.katalog_film.core.ui.detail.DetailActivity
import com.candra.katalog_film.core.ui.movie.MovieViewModel
import com.candra.katalog_film.core.ui.tvshow.TvViewModel
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper
import com.candra.katalog_film.core.utils.Helper.constLinearLayoutManager
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.ViewAllLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAllActivity: AppCompatActivity(){

    private lateinit var binding: ViewAllLayoutBinding
    private val horizontalAdapterMovie by lazy { MovieAdapter(::onClickListener) }
    private val movieViewModel by viewModels<MovieViewModel>()
    private val tvShowViewModel by viewModels<TvViewModel>()
    private val horizontalAdapterTvShow by lazy { TvShowAdapter(::onClickTvShow) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewAllLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDefaultForPage()
        setAutoCompleteTextView()
        setDarkAndLightComponent()
    }

    private fun setDarkAndLightComponent(){
        binding.toolbarViewAll.apply {
            setNavigationIcon(
                if (isDarkMode) R.drawable.ic_baseline_close_24
                else R.drawable.ic_baseline_close_24_black
            )
            setNavigationOnClickListener {
               onBackPressed()
            }
        }
    }

    private fun setToolbar(content: String,title2: String){
        binding.apply {
            setSupportActionBar(binding.toolbarViewAll)
            toolbarViewAll.apply {
                title = getString(R.string.name_developer)
                subtitle = formatContentString(content,title2)
            }
        }
    }

    private fun formatContentString(title: String,title2: String): String{
        val contentString = getString(R.string.view_all_detail)
        return String.format(contentString,title2,title)
    }

    private fun setLayoutManager(position: Int){
        if (position == 1) {
            binding.rvPage.apply {
                adapter = horizontalAdapterMovie
                layoutManager = constLinearLayoutManager(context, 3)
            }
        }else {
            binding.rvPage.apply {
                adapter = horizontalAdapterTvShow
                layoutManager = constLinearLayoutManager(context,3)
            }
        }
    }

    private fun setDefaultForPage(){
        binding.apply {
            intent.extras?.getString(Constant.EXTRA_VIEW_ALL)?.let {
                if (it == "movie"){
                    intent.extras?.getInt(Constant.POSITION)?.let { position ->
                        when(position){
                            1 -> {
                                movieViewModel.moviePlayingNow(1).observe(this@ViewAllActivity){ states ->
                                    setListMovie(states)
                                }
                                setToolbar(getString(R.string.playing_now),getString(R.string.movie))
                                autoCompleteTextPage.setText("1")
                            }
                            2 -> {
                                movieViewModel.movieRecomended(1).observe(this@ViewAllActivity){states ->
                                    setListMovie(states)
                                }
                                setToolbar(getString(R.string.recomended_movie),getString(R.string.movie))
                                autoCompleteTextPage.setText("1")
                            }
                            3 -> {
                                movieViewModel.movieTopRated(1).observe(this@ViewAllActivity){states ->
                                    setListMovie(states)
                                }
                                setToolbar(getString(R.string.top_rated),getString(R.string.movie))
                                autoCompleteTextPage.setText("1")
                            }
                        }
                    }
                }else if (it == "tv_show"){
                    intent.extras?.getInt(Constant.POSITION)?.let { position ->
                        when(position){
                            1 -> {
                                tvShowViewModel.tvPlayingNow(1).observe(this@ViewAllActivity){ states ->
                                   setListTvShow(states)
                                }
                                setToolbar(getString(R.string.playing_now),getString(R.string.tv_show))
                                autoCompleteTextPage.setText("1")
                            }
                            2 -> {
                                tvShowViewModel.tvRecomended(1).observe(this@ViewAllActivity){states ->
                                    setListTvShow(states)
                                }
                                setToolbar(getString(R.string.recomended_movie),getString(R.string.tv_show))
                                autoCompleteTextPage.setText("1")
                            }
                            3 -> {
                                tvShowViewModel.tvTopRated(1)
                                    .observe(this@ViewAllActivity) { states ->
                                        setListTvShow(states)
                                    }
                                setToolbar(getString(R.string.top_rated),getString(R.string.tv_show))
                                autoCompleteTextPage.setText("1")
                            }
                        }
                    }
                }
            }

        }
    }

    private fun setAutoCompleteTextView(){
        val arrayPage = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
        val adapterPage = ArrayAdapter(this@ViewAllActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayPage)
        binding.autoCompleteTextPage.apply {
            setAdapter(adapterPage)
            setOnItemClickListener { _, _, _, _ ->
                val position = binding.autoCompleteTextPage.text.toString().toInt()
                intent.extras?.getString(Constant.EXTRA_VIEW_ALL)?.let {
                    if (it == "movie"){
                        intent.extras?.getInt(Constant.POSITION)?.let { data ->
                            when(data){
                                1 -> {
                                    movieViewModel.moviePlayingNow(position).observe(this@ViewAllActivity){ states ->
                                        setListMovie(states)
                                    }
                                }
                                2 -> {
                                    movieViewModel.movieRecomended(position).observe(this@ViewAllActivity){states ->
                                        setListMovie(states)
                                    }
                                }
                                3 -> {
                                    movieViewModel.movieTopRated(position).observe(this@ViewAllActivity){ states ->
                                        setListMovie(states)
                                    }
                                }
                            }
                        }
                    }else if (it == "tv_show"){
                        intent.extras?.getInt(Constant.POSITION)?.let { data ->
                            when(data){
                                1 -> {
                                    tvShowViewModel.tvPlayingNow(position).observe(this@ViewAllActivity){ states ->
                                        setListTvShow(states)
                                    }
                                }
                                2 -> {
                                    tvShowViewModel.tvRecomended(position).observe(this@ViewAllActivity){states ->
                                        setListTvShow(states)
                                    }
                                }
                                3 -> {
                                    tvShowViewModel.tvTopRated(position).observe(this@ViewAllActivity)
                                    { states ->
                                        setListTvShow(states)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setListMovie(listMovie: States<List<Movie>>){
        binding.apply {
            when(listMovie){
                is States.Loading -> {
                    Helper.showProgressBar(true,progressBarViewAll,rvPage)
                    viewErrorLayout.root.visibility = View.GONE
                }
                is States.Success -> {
                    Helper.showProgressBar(false,progressBarViewAll,rvPage)
                    viewErrorLayout.root.visibility = View.GONE
                    horizontalAdapterMovie.temptDataAll(listMovie.data)
                    setLayoutManager(1)
                }
                is States.Failed -> {
                    fetchDataFailedFromServer()
                    Log.d("ViewAllActivity", "setListMovie: ${listMovie.message}")
                }
                else -> {}
            }
        }
    }

    private fun setListTvShow(listTvShow: States<List<TvShow>>){
        binding.apply {
            when(listTvShow){
                is States.Loading ->{
                    Helper.showProgressBar(true,progressBarViewAll,rvPage)
                    viewErrorLayout.root.visibility = View.GONE
                }
                is States.Success -> {
                    horizontalAdapterTvShow.temptAllDataTvShow(listTvShow.data)
                    Helper.showProgressBar(false,progressBarViewAll,rvPage)
                    viewErrorLayout.root.visibility = View.GONE
                    setLayoutManager(2)
                }
                is States.Failed -> {
                    Log.i("ViewAllActivity", "setListTvShow: ${listTvShow.message}")
                    fetchDataFailedFromServer()
                }
                else -> {}
            }
        }
    }

    private fun fetchDataFailedFromServer() {
        binding.apply {
            progressBarViewAll.visibility = View.GONE
            rvPage.visibility = View.GONE
            viewErrorLayout.apply {
                root.visibility = View.VISIBLE
                mtvEmptyText.text = getString(R.string.error)
            }
        }
    }

    private fun onClickListener(movie: Movie){
        Intent(this@ViewAllActivity,DetailActivity::class.java).apply {
            putExtra(Constant.POSITION,1)
            putExtra(Constant.EXTRA_MOVIE,movie)
        }.also { startActivity(it) }
    }

    private fun onClickTvShow(tvShow: TvShow){
        Intent(this@ViewAllActivity,DetailActivity::class.java).apply {
            putExtra(Constant.POSITION,2)
            putExtra(Constant.EXTRA_TV,tvShow)
        }.also { startActivity(it) }
    }
}