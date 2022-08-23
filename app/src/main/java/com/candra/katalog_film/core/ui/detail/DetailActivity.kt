package com.candra.katalog_film.core.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.candra.katalog_film.R
import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.*
import com.candra.katalog_film.core.ui.adapter.genreadapter.GenreAdapter
import com.candra.katalog_film.core.ui.adapter.movieadapter.MovieAdapter
import com.candra.katalog_film.core.ui.adapter.peopleadapter.CastingAdapter
import com.candra.katalog_film.core.ui.adapter.peopleadapter.CrewAdapter
import com.candra.katalog_film.core.ui.adapter.traileradapter.TrailerAdapter
import com.candra.katalog_film.core.ui.adapter.tvshowadapter.TvShowAdapter
import com.candra.katalog_film.core.ui.people.CastingViewModel
import com.candra.katalog_film.core.ui.people.CrewViewModel
import com.candra.katalog_film.core.ui.people.detailpeople.DetailPeople
import com.candra.katalog_film.core.utils.Constant.EXTRA_CASTING
import com.candra.katalog_film.core.utils.Constant.EXTRA_CREW
import com.candra.katalog_film.core.utils.Constant.EXTRA_MOVIE
import com.candra.katalog_film.core.utils.Constant.EXTRA_TV
import com.candra.katalog_film.core.utils.Constant.IMAGE_PATH
import com.candra.katalog_film.core.utils.Constant.POSITION
import com.candra.katalog_film.core.utils.Constant.URL_YOUTUBE
import com.candra.katalog_film.core.utils.Event
import com.candra.katalog_film.core.utils.FormatContent
import com.candra.katalog_film.core.utils.FormatContent.formatContentDetail
import com.candra.katalog_film.core.utils.Helper
import com.candra.katalog_film.core.utils.Helper.contentImage
import com.candra.katalog_film.databinding.DetailLayoutBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class DetailActivity: AppCompatActivity(){

    private lateinit var binding: DetailLayoutBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val castingViewModel by viewModels<CastingViewModel>()
    private val crewViewModel by viewModels<CrewViewModel>()
    private val adapterCrew by lazy { CrewAdapter(::onClickCrew) }
    private val adapterCasting by lazy { CastingAdapter(::onClickCasting) }
    private val adapterTrailer by lazy { TrailerAdapter(::onClickTrailerToYoutube) }
    private val adapterMain by lazy { MovieAdapter(::onClickMovie) }
    private val adapterMainTvShow by lazy { TvShowAdapter(::onClickTvShow) }
    private var isFavorite = false
    private val adapterGenre by lazy { GenreAdapter() }
    private var idMovie: Int = 0
    private var idTvShow: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromActivity()
        binding.toolbarDetail.setNavigationOnClickListener {
            onBackPressed()
        }
        observerData()
        setLayoutManager()
    }

    private fun shareData(any: Any){
        when(any){
            is Movie -> {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT,"Title: ${any.title} \n Overview: ${any.overview} \n " +
                            "Release Date: ${FormatContent.parsingDateFormat(any.releaseData)}")
                }.also { startActivity(it) }
            }
            is TvShow -> {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT,"Title: ${any.title} \n Overview: ${any.overview} \n " +
                            "Release Date: ${FormatContent.parsingDateFormat(any.releaseDate)}")
                }.also { startActivity(it) }
            }
        }
    }

    private fun getDataFromActivity(){
        val position = intent.getIntExtra(POSITION,0)
        if (position == 1){
            showDetailMovie()
        }else if (position == 2){
            showDetailTvShow()
        }
    }

    private fun setLayoutManager(){
        binding.apply {
            rvGenre.apply {
                adapter = adapterGenre
                layoutManager = Helper.constLinearLayoutManager(this@DetailActivity,3)
            }
            rvCrew.apply {
                adapter = adapterCrew
                layoutManager = Helper.constLinearLayoutManager(this@DetailActivity,1)
            }
            rvCast.apply {
                adapter = adapterCasting
                layoutManager = Helper.constLinearLayoutManager(this@DetailActivity,1)
            }
            rvTrailer.apply {
                adapter = adapterTrailer
                layoutManager = Helper.constLinearLayoutManager(this@DetailActivity,2)
            }
        }
    }

    private fun setCrew(crew: States<List<Crew>>){
        binding.apply {
            when(crew){
                is States.Loading -> Helper.showProgressBar(true,progressBarCrew,rvCrew)
                is States.Success -> {
                    adapterCrew.submitListData(crew.data)
                    Helper.showProgressBar(false,progressBarCrew,rvCrew)
                }
                is States.Failed -> {
                    Helper.showProgressBar(false,progressBarCrew,rvCrew)
                }
                else -> {}
            }
        }
    }

    private fun setVideoTrailer(videoTrailer: States<List<Trailer>>){
        binding.apply {
            when(videoTrailer){
                is States.Loading -> Helper.showProgressBar(true,progressBarTrailer,rvTrailer)
                is States.Success -> {
                    Helper.showProgressBar(false,progressBarTrailer,rvTrailer)
                    adapterTrailer.submitListData(videoTrailer.data)
                }
                is States.Failed -> {
                    Helper.showProgressBar(false,progressBarTrailer,rvTrailer)
                }
                else -> {}
            }
        }
    }

    private fun onClickTrailerToYoutube(trailer: Trailer){
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(URL_YOUTUBE + trailer.key)
        }.also { startActivity(it) }
    }

    private fun setCasting(casting: States<List<Casting>>){
        binding.apply {
            when(casting){
                is States.Loading -> Helper.showProgressBar(true,progressBarCast,rvCast)
                is States.Success -> {
                    Helper.showProgressBar(false,progressBarCast,rvCast)
                    adapterCasting.submitListData(casting.data)
                }
                is States.Failed -> {
                    Helper.showProgressBar(false,progressBarCast,rvCast)
                }
                else -> {}
            }
        }
    }

    private fun setDataGenre(dataGenre: States<List<Genres>>){
        when(dataGenre){
            is States.Success ->{
                showLoading(false)
                adapterGenre.submitListDataGenre(dataGenre.data)
                Log.d("TAG", "setDataGenre: ${dataGenre.data}")
            }
            is States.Failed ->{
                Log.d("TAG", "setDataGenre: ${dataGenre.message}")
                showLoading(false)
            }
            is States.Loading -> showLoading(true)
            else -> {}
        }
    }

    private fun showLoading(isShow: Boolean){
        binding.apply {
            rvGenre.visibility = if (isShow) View.GONE else View.VISIBLE
            progressBarGenre.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    private fun showDetailMovie(){
        intent.getParcelableExtra<Movie>(EXTRA_MOVIE)?.let { movie ->
            with(binding){
                tvName.text = movie.title
                tvRating.text = "${movie.voteAverage} /10"
                tvPopularity.text = movie.popularity.toString()
                tvOverview.text = movie.overview
                tvName.isSelected = true
                val textRelease = if (movie.releaseData == "") getString(R.string.data_is_null)
                else FormatContent.parsingDateFormat(movie.releaseData)
                tvRelease.text = textRelease

                val newValue = movie.voteAverage.toFloat()
                ratingBar.apply {
                    numStars = 5
                    stepSize = 0.5f
                    rating = newValue / 2
                }
                imgPhoto.contentImage(IMAGE_PATH + movie.thumbnail,this@DetailActivity)
                imgCover.contentImage(IMAGE_PATH + movie.thumbnail,this@DetailActivity)
                toolbarDetail.subtitle = movie.title.formatContentDetail(
                    this@DetailActivity,1
                )

                idMovie = movie.id

                detailViewModel.getRecommendationMovie(idMovie).observe(this@DetailActivity){
                    setRecommendationMovie(it)
                }

                castingViewModel.getCastingMovie(idMovie).observe(this@DetailActivity){
                    setCasting(it)
                }

                detailViewModel.getGenreMovie(idMovie).observe(this@DetailActivity){
                    setDataGenre(it)
                }

                crewViewModel.getCrewMovie(idMovie).observe(this@DetailActivity){
                    setCrew(it)
                }

                detailViewModel.getVideosMovie(idMovie).observe(this@DetailActivity){
                    setVideoTrailer(it)
                }

                fabShare.setOnClickListener {
                    shareData(movie)
                }

                detailViewModel.isFavoriteFromFavorite(movie.title)

                fabFavorite.setOnClickListener {
                    if (isFavorite) detailViewModel.removeMovieFromFavorite(movie,movie.title,this@DetailActivity)
                    else detailViewModel.insertMovieToFavorite(movie,movie.title,this@DetailActivity)
                }

                rvRecomendation.apply {
                    adapter = adapterMain
                    layoutManager = Helper.constLinearLayoutManager(this@DetailActivity,1)
                }
            }
        }
    }

    private fun observerData(){
        detailViewModel.isFavorite.observe(this@DetailActivity){
            isFavorite = it
            binding.fabFavorite.setFavorite(it,this@DetailActivity)
        }
        detailViewModel.snackBarText.observe(this@DetailActivity,this::showSnackBar)

    }

    private fun showSnackBar(eventMessage: Event<String>){
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            binding.containerLayoutDetail,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showDetailTvShow(){
        intent.getParcelableExtra<TvShow>(EXTRA_TV)?.let { tvShow ->
            with(binding){
                tvName.text = tvShow.title
                tvRating.text = "${tvShow.voteAverage} /10"
                tvPopularity.text = tvShow.popularity.toString()
                tvOverview.text = tvShow.overview

                val textRelease = if(tvShow.releaseDate == "") getString(R.string.data_is_null)
                else FormatContent.parsingDateFormat(tvShow.releaseDate)

                tvRelease.text = textRelease

                ratingBar.apply {
                    numStars = 5
                    stepSize = 0.5f
                    rating = tvShow.voteAverage.toFloat() / 2
                }
                imgPhoto.contentImage(IMAGE_PATH + tvShow.thumbnail,this@DetailActivity)
                imgCover.contentImage(IMAGE_PATH + tvShow.cover,this@DetailActivity)
                toolbarDetail.subtitle = tvShow.title.formatContentDetail(
                    this@DetailActivity,2
                )

                detailViewModel.isFavoriteFromFavorite(tvShow.title)

                fabShare.setOnClickListener {
                    shareData(tvShow)
                }

                idTvShow = tvShow.id

                detailViewModel.getRecommendationTvShow(idTvShow).observe(this@DetailActivity){
                    setRecommendationTvShow(it)
                }

                crewViewModel.getCrewTvShow(idTvShow).observe(this@DetailActivity){
                    setCrew(it)
                }

                castingViewModel.getCastingTvShow(idTvShow).observe(this@DetailActivity){
                    setCasting(it)
                }

                detailViewModel.getGenreTvShow(idTvShow).observe(this@DetailActivity){
                    setDataGenre(it)
                }

                detailViewModel.getVideoTvShow(idTvShow).observe(this@DetailActivity){
                    setVideoTrailer(it)
                }

                fabFavorite.setOnClickListener {
                    if (isFavorite) detailViewModel.removeTvShowFromFavorite(tvShow,tvShow.title,this@DetailActivity)
                    else detailViewModel.insertTvShowToFavorite(tvShow,this@DetailActivity,tvShow.title)
                }
                rvRecomendation.apply {
                    adapter = adapterMainTvShow
                    layoutManager = Helper.constLinearLayoutManager(this@DetailActivity,1)
                }
            }
        }
    }


    private fun onClickCasting(casting: Casting){
        Intent(this@DetailActivity,DetailPeople::class.java).apply {
            putExtra(POSITION,1)
            putExtra(EXTRA_CASTING,casting)
        }.also { startActivity(it) }
    }


    private fun onClickCrew(crew: Crew){
        Intent(this@DetailActivity,DetailPeople::class.java).apply {
            putExtra(POSITION,2)
            putExtra(EXTRA_CREW,crew)
        }.also { startActivity(it) }
    }

    private fun FloatingActionButton.setFavorite(isStatusFavorite: Boolean, context: Context){
        if (isStatusFavorite){
            this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24))
        }else{
            this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24))
        }
    }

    private fun setRecommendationMovie(movieRecommendation: States<List<Movie>>){
        binding.apply {
            when(movieRecommendation){
                is States.Loading -> Helper.showProgressBar(true,progressBarRecomendation,rvRecomendation)
                is States.Success -> {
                    Helper.showProgressBar(false,progressBarRecomendation,rvRecomendation)
                    adapterMain.temptDataAll(movieRecommendation.data)
                    Log.d("DetailActivity", "setRecommendationMovie: ${movieRecommendation.data}")
                }
                is States.Failed -> {

                    Log.d("DetailActivity", "setTrendingDayMovie: ${movieRecommendation.message}")
                }
                else -> {

                }
            }
        }
    }

    private fun setRecommendationTvShow(tvShowRecommendation: States<List<TvShow>>){
        binding.apply {
            when(tvShowRecommendation){
                is States.Loading -> Helper.showProgressBar(true,progressBarRecomendation,rvRecomendation)
                is States.Success -> {
                    Helper.showProgressBar(false,progressBarRecomendation,rvRecomendation)
                    adapterMainTvShow.temptAllDataTvShow(tvShowRecommendation.data)
                    Log.d("DetailActivity", "setRecommendationTvShow: ${tvShowRecommendation.data}")
                }
                is States.Failed -> {
                    Helper.showProgressBar(false,progressBarRecomendation,rvRecomendation)
                    Log.d("DetailActivity", "setRecommendationTvShow: ${tvShowRecommendation.message}")
                }
                is States.Empty -> {
                    textEmpty.text = getString(R.string.empty_data)
                    Helper.showProgressBar(false,progressBarRecomendation,rvRecomendation)
                    textEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onClickMovie(movie: Movie){
       Intent(this@DetailActivity,DetailActivity::class.java).apply {
           putExtra(POSITION,1)
           putExtra(EXTRA_MOVIE,movie)
       }.also { startActivity(it) }
        finish()
    }

    private fun onClickTvShow(tvShow: TvShow){
        Intent(this@DetailActivity,DetailActivity::class.java).apply {
            putExtra(POSITION,2)
            putExtra(EXTRA_TV,tvShow)
        }.also { startActivity(it) }
        finish()
    }
}
