package com.candra.katalog_film.core.ui.tvshow

import android.annotation.SuppressLint
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
import com.candra.katalog_film.core.domain.model.TvShow
import com.candra.katalog_film.core.ui.adapter.tvshowadapter.TvRecomendedAdapter
import com.candra.katalog_film.core.ui.adapter.tvshowadapter.TvShowAdapter
import com.candra.katalog_film.core.ui.detail.DetailActivity
import com.candra.katalog_film.core.ui.favorite.FavoriteActivity
import com.candra.katalog_film.core.ui.tempt.ViewAllActivity
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper
import com.candra.katalog_film.core.utils.Helper.constLinearLayoutManager
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.MovieLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class TvShowFragment: Fragment()
{
    private var _binding: MovieLayoutBinding? = null
    private val tvShowAdapter by lazy { TvShowAdapter(::onClick) }
    private val tvRecomendedAdapter by lazy { TvRecomendedAdapter(::onClick) }
    private val tvShowViewModel by viewModels<TvViewModel>()

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
        observerAllData()
        setCompnentAll()
        setAdapterAll(1)
        setAdapterAll(2)
        setAdapterAll(3)
        setAdapterAll(4)
        setAdapterAll(5)

        fabFavoriteShrinkAndExtend()
        binding.apply {
            toolbarMovie.subtitle = getString(R.string.tv_show)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun onClick(tvShow: TvShow) {
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(Constant.POSITION,2)
            putExtra(Constant.EXTRA_TV,tvShow)
        }.also { startActivity(it) }
    }

    private fun failedFetchDataFromInternet(){
        binding.apply {
            viewError.mtvEmptyText.text = requireActivity().getString(R.string.error)
            viewError.root.visibility = View.VISIBLE
            nestedScroolView.visibility = View.GONE
        }
    }


    private fun observerAllData(){
        tvShowViewModel.tvPlayingNow(1).observe(viewLifecycleOwner,this::showPlayingTvShow)
        tvShowViewModel.tvTrendingWeek(Constant.DAY).observe(viewLifecycleOwner){ states ->
            showTrendingWeekTvShow(states,1)
        }
        tvShowViewModel.tvTrendingWeek(Constant.WEEK).observe(viewLifecycleOwner){states ->
            showTrendingWeekTvShow(states,2)
        }
        tvShowViewModel.tvRecomended(1).observe(viewLifecycleOwner,this::showTvShowRecomended)
        tvShowViewModel.tvTopRated(1).observe(viewLifecycleOwner,this::setTvShowTopRated)
    }

    private fun setTvShowTopRated(states: States<List<TvShow>>){
        binding.apply {
            when(states){
                is States.Loading -> Helper.showProgressBar(true,progressBarTopRated,rvTopRated)
                is States.Success -> {
                    Helper.showProgressBar(false,progressBarTopRated,rvTopRated)
                    tvShowAdapter.temptAllDataTvShow(states.data)
                }
                is States.Failed -> {failedFetchDataFromInternet()}
                else -> {}
            }
        }
    }

    private fun showTvShowRecomended(states: States<List<TvShow>>){
        when(states){
            is States.Loading -> showLoading(true,3)
            is States.Success -> {
                showLoading(false,3)
                tvRecomendedAdapter.temptAllDataTvShowRecomended(states.data)
            }
            is States.Failed -> {
                failedFetchDataFromInternet()
            }
            else -> {
                showLoading(false,3)
            }
        }
    }

    private fun showTrendingWeekTvShow(states: States<List<TvShow>>,position: Int){
        binding.apply {
            when(states){
                is States.Loading -> {
                    if (position == 1) showLoading(true,2)
                    else if(position == 2) showLoading(true,4)
                }
                is States.Success -> {
                    if (position == 1) showLoading(false,2)
                    else if(position == 2)showLoading(false,4)
                    tvShowAdapter.temptAllDataTvShow(states.data)
                    Log.d("TAG", "showTrendingWeekTvShow: ${states.data}")
                }
                is States.Failed -> {
                    failedFetchDataFromInternet()
                }
                else -> {
                    if (position == 1)showLoading(false,2)
                    else if (position == 2) showLoading(false,4)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showPlayingTvShow(state: States<List<TvShow>>){
        when(state){
            is States.Loading -> {
                showLoading(true,1)
            }
            is States.Success -> {
                tvShowAdapter.temptAllDataTvShow(state.data)
                showLoading(false,1)
            }
            is States.Failed -> {
                failedFetchDataFromInternet()
            }
            else -> {}
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
                4 ->{
                    progressBarTrendingWeek.visibility = if (isLoading) View.VISIBLE else View.GONE
                    rvTrendingWeek.visibility = if (isLoading) View.GONE else View.VISIBLE
                }
            }
        }
    }


    private fun setCompnentAll(){
        binding.apply {
            playingNowText.setTextColor(if (requireActivity().isDarkMode) ContextCompat.getColor(
                requireActivity(), R.color.white
            ) else ContextCompat.getColor(requireActivity(), R.color.black))

            recomendedMovie.setTextColor(if(requireActivity().isDarkMode)
                ContextCompat.getColor(requireActivity(), R.color.white)
            else ContextCompat.getColor(requireActivity(), R.color.black))

            trendingDays.setTextColor(if(requireActivity().isDarkMode)
                ContextCompat.getColor(requireActivity(),R.color.white)
            else ContextCompat.getColor(requireActivity(),R.color.black))

            trendingWeek.setTextColor(if(requireActivity().isDarkMode)
                ContextCompat.getColor(requireActivity(),R.color.white)
            else ContextCompat.getColor(requireActivity(),R.color.black))

            topRated.setTextColor(if (requireActivity().isDarkMode)
                ContextCompat.getColor(requireActivity(),R.color.white)
            else ContextCompat.getColor(requireActivity(),R.color.black))
        }
    }

    // TODO: Make a money from Google Adsense
    // TODO : Make a new feature getLatest a TvShow and getUpComing a TvShow
    private fun toViewAllActivity(position: Int){
        when(position){
            1 -> {
                Intent(requireActivity(),ViewAllActivity::class.java).apply {
                    putExtra(Constant.EXTRA_VIEW_ALL,"tv_show")
                    putExtra(Constant.POSITION,1)
                }.also { startActivity(it) }
            }
            2 -> {
                Intent(requireActivity(),ViewAllActivity::class.java).apply {
                    putExtra(Constant.EXTRA_VIEW_ALL,"tv_show")
                    putExtra(Constant.POSITION,2)
                }.also { startActivity(it) }
            }
            3 -> {
                Intent(requireActivity(),ViewAllActivity::class.java).apply {
                    putExtra(Constant.EXTRA_VIEW_ALL,"tv_show")
                    putExtra(Constant.POSITION,3)
                }.also { startActivity(it) }
            }
        }
    }

    private fun setAdapterAll(position: Int){
        when(position){
            1 -> {
                binding.rvPlayingNow.apply {
                    layoutManager = constLinearLayoutManager(requireActivity(),1)
                    adapter = tvShowAdapter
                }
            }
            2 -> {
                binding.rvRecomendedMovie.apply {
                    layoutManager = constLinearLayoutManager(requireActivity(),2)
                    adapter = tvRecomendedAdapter
                }
            }
            3 -> binding.rvTrendingNowDays.apply {
                layoutManager = constLinearLayoutManager(requireActivity(),1)
                adapter = tvShowAdapter
            }
            4 -> binding.rvTrendingWeek.apply {
                layoutManager = constLinearLayoutManager(requireActivity(),1)
                adapter = tvShowAdapter
            }
            5 -> binding.rvTopRated.apply {
                layoutManager = constLinearLayoutManager(requireActivity(),1)
                adapter = tvShowAdapter
            }
        }
    }

    private fun toFavoriteTvShow(){
        startActivity(Intent(requireActivity(),FavoriteActivity::class.java))
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
            fabFavoriteMovie.setOnClickListener {
                toFavoriteTvShow()
            }
        }
    }

}