package com.candra.katalog_film.core.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.candra.katalog_film.R
import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.ui.adapter.movieadapter.MovieAdapter
import com.candra.katalog_film.core.ui.adapter.movieadapter.RecomendedMovieAdapter
import com.candra.katalog_film.core.ui.detail.DetailActivity
import com.candra.katalog_film.core.ui.favorite.FavoriteActivity
import com.candra.katalog_film.core.ui.tempt.ViewAllActivity
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.MovieLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment: Fragment()
{

    private val adapterMain by lazy { MovieAdapter(::onClick) }
    private val adapterRecomended by lazy { RecomendedMovieAdapter(::onClick) }

    private val movieViewModel by viewModels<MovieViewModel>()

    private var _binding: MovieLayoutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterAll(1)
        setAdapterAll(2)
        setAdapterAll(3)
        setAdapterAll(4)
        setAdapterAll(5)
        setComponentAll()
        observerAllData()
        binding.apply {
            fabFavoriteMovie.setOnClickListener {
                toFavoriteDetail()
            }
            btnViewAllPlayingNow.setOnClickListener {
               toViewAllActivity(1)
            }
            btnViewAllRecomendedMovie.setOnClickListener {
                toViewAllActivity(2)
            }
            btnViewAllTopRated.setOnClickListener {
                toViewAllActivity(3)
            }
        }
    }


    private fun toViewAllActivity(position: Int){
        when(position){
            1 -> {
                Intent(requireActivity(),ViewAllActivity::class.java).apply {
                    putExtra(Constant.EXTRA_VIEW_ALL,"movie")
                    putExtra(Constant.POSITION,1)
                }.also { startActivity(it) }
            }
            2 -> {
                Intent(requireActivity(),ViewAllActivity::class.java).apply {
                    putExtra(Constant.EXTRA_VIEW_ALL,"movie")
                    putExtra(Constant.POSITION,2)
                }.also { startActivity(it) }
            }
            3 -> {
                Intent(requireActivity(),ViewAllActivity::class.java).apply {
                    putExtra(Constant.EXTRA_VIEW_ALL,"movie")
                    putExtra(Constant.POSITION,3)
                }.also { startActivity(it) }
            }
        }
    }


    private fun setComponentAll(){
        binding.apply {
            playingNowText.setTextColor(if (requireActivity().isDarkMode) ContextCompat.getColor(
                requireActivity(), R.color.white
            ) else ContextCompat.getColor(requireActivity(),R.color.black))

            recomendedMovie.setTextColor(if(requireActivity().isDarkMode)
                ContextCompat.getColor(requireActivity(),R.color.white)
            else ContextCompat.getColor(requireActivity(),R.color.black))

            trendingWeek.setTextColor(if (requireActivity().isDarkMode) ContextCompat.getColor(
                requireActivity(),R.color.white
            ) else ContextCompat.getColor(requireActivity(),R.color.black))

            trendingDays.setTextColor(if (requireActivity().isDarkMode) ContextCompat.getColor(
                requireActivity(),R.color.white
            ) else ContextCompat.getColor(requireActivity(),R.color.black))

            topRated.setTextColor(if (requireActivity().isDarkMode) ContextCompat.getColor(
                requireActivity(),R.color.white
            ) else ContextCompat.getColor(requireActivity(),R.color.black))

            fabFavoriteShrinkAndExtend()
        }
    }


    private fun observerAllData(){
        movieViewModel.moviePlayingNow(1).observe(viewLifecycleOwner,this::setAllDataMoviePlayingNow)
        movieViewModel.movieTrendingDay(Constant.DAY).observe(viewLifecycleOwner){
            setTrendingDayMovie(it,1)
        }
        movieViewModel.movieTrendingDay(Constant.WEEK).observe(viewLifecycleOwner){
            setTrendingDayMovie(it,2)
        }
        movieViewModel.movieRecomended(1).observe(viewLifecycleOwner,this::setMovieRecomended)
        movieViewModel.movieTopRated(1).observe(viewLifecycleOwner,this::setTopRatedMovie)
    }

    private fun onClick(movie: Movie){
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(Constant.POSITION,1)
            putExtra(Constant.EXTRA_MOVIE,movie)
        }.also { startActivity(it) }
    }

    private fun setMovieRecomended(movieRecomended: States<List<Movie>>){
        when(movieRecomended){
            is States.Loading -> {
                showLoading(true,3)
            }
            is States.Success -> {
                showLoading(false,3)
                adapterRecomended.temptAllData(movieRecomended.data)
            }
            is States.Failed -> {
                failedFetchDataFromInternet()
            }
            else -> {
                failedFetchDataFromInternet()
            }
        }
    }

    private fun setTrendingDayMovie(movieTrendingDay: States<List<Movie>>,position: Int){
        when(movieTrendingDay){
            is States.Loading -> {
                if (position == 1) showLoading(true,2)
                else if (position == 2) showLoading(true,4)
            }
            is States.Success -> {
                if (position == 1) showLoading(false,2)
                else if (position == 2) showLoading(false,4)
                adapterMain.temptDataAll(movieTrendingDay.data)
            }
            is States.Failed -> {
                failedFetchDataFromInternet()
            }
            else -> {
                failedFetchDataFromInternet()
            }
        }
    }

    private fun setAdapterAll(position: Int){
        when(position){
            1 -> {
                binding.rvPlayingNow.apply {
                    layoutManager = Helper.constLinearLayoutManager(requireActivity(),1)
                    adapter = adapterMain
                }
            }
            2 -> {
                binding.rvTrendingNowDays.apply {
                    layoutManager = Helper.constLinearLayoutManager(requireActivity(),1)
                    adapter = adapterMain
                }
            }
            3 -> {
                binding.rvRecomendedMovie.apply {
                    layoutManager = Helper.constLinearLayoutManager(requireActivity(),2)
                    adapter = adapterRecomended
                }
            }
            4 -> {
                binding.rvTrendingWeek.apply {
                    layoutManager = Helper.constLinearLayoutManager(requireActivity(),1)
                    adapter = adapterMain
                }
            }
            5 -> {
                binding.rvTopRated.apply {
                    layoutManager = Helper.constLinearLayoutManager(requireActivity(),1)
                    adapter = adapterMain
                }
            }
        }
    }

    private fun setTopRatedMovie(states: States<List<Movie>>){
        binding.apply {
            when(states){
                is States.Loading -> Helper.showProgressBar(true,progressBarTopRated,rvTopRated)
                is States.Success -> {
                    Helper.showProgressBar(false,progressBarTopRated,rvTopRated)
                    adapterMain.temptDataAll(states.data)
                }
                is States.Failed -> {
                    failedFetchDataFromInternet()
                }
                else -> {failedFetchDataFromInternet()}
            }
        }
    }

    private fun toFavoriteDetail(){
        startActivity(Intent(requireActivity(),FavoriteActivity::class.java))
    }

    private fun setAllDataMoviePlayingNow(data: States<List<Movie>>){
        when(data){
            is States.Loading -> {
                showLoading(true,1)
            }
            is States.Success -> {
                showLoading(false,1)
                adapterMain.temptDataAll(data.data)
                Log.d("MovieFragment", "setMovieRecomended: ${data.data}")
            }
            is States.Failed -> {
                failedFetchDataFromInternet()
            }
            else -> {
                failedFetchDataFromInternet()
            }
        }
    }

    private fun failedFetchDataFromInternet(){
        binding.apply {
            viewError.mtvEmptyText.text = getString(R.string.error)
            viewError.root.visibility = View.VISIBLE
            nestedScroolView.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean,position: Int){
        binding.apply {
            when(position){
                1 -> {
                    progresBarNowPlaying.visibility = if (isLoading) View.VISIBLE else View.GONE
                    rvPlayingNow.visibility = if (isLoading) View.GONE else View.VISIBLE
                }
                2 -> {
                    progresBarTrendingDays.visibility = if (isLoading) View.VISIBLE else View.GONE
                    rvTrendingNowDays.visibility = if (isLoading) View.GONE else View.VISIBLE
                }
                3 -> {
                    progressBarRecomendedMovie.visibility = if (isLoading) View.VISIBLE else View.GONE
                    rvRecomendedMovie.visibility = if (isLoading) View.GONE else View.VISIBLE
                }
                4 -> {
                    progressBarTrendingWeek.visibility = if (isLoading) View.VISIBLE else View.GONE
                    rvTrendingWeek.visibility = if (isLoading) View.GONE else View.VISIBLE
                }
            }
        }
    }

    private fun fabFavoriteShrinkAndExtend(){
        binding.apply {
            nestedScroolView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{ _, _, scrollY, _, oldScrollY ->
                if (scrollY < oldScrollY){
                    fabFavoriteMovie.extend()
                }else{
                    fabFavoriteMovie.shrink()
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}