package com.candra.katalog_film.core.ui.search_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.katalog_film.R
import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.ui.adapter.movieadapter.RecomendedMovieAdapter
import com.candra.katalog_film.core.ui.detail.DetailActivity
import com.candra.katalog_film.core.ui.search.SearchViewModel
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.databinding.TvShowSearchLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment: Fragment()
{

    //TODO: Run All Component In Android
    private var _binding: TvShowSearchLayoutBinding? = null
    private val binding get() = _binding!!
    private val adapterMovie by lazy { RecomendedMovieAdapter(::onClick) }
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TvShowSearchLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterAllData()
        setDataAllMovie()
    }

    private fun setAdapterAllData(){
        binding.rvSearch.apply {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun setDataAllMovie(){
        binding.apply {
            edtSearch.addTextChangedListener {
                val inputSearchUser = it.toString()
                if (inputSearchUser.isEmpty()){
                    setEmptyRecyclerView(true)
                }else{
                    searchViewModel.searchMovie(inputSearchUser).observe(viewLifecycleOwner){ states ->
                        when(states){
                            is States.Loading -> setProgressBar(true)
                            is States.Success -> {
                                setProgressBar(false)
                                adapterMovie.temptAllData(states.data)
                            }
                            is States.Failed -> {
                                setFailed(true)
                            }
                            is States.Empty -> {
                                setEmpty(true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setEmptyRecyclerView(isShowEmpty: Boolean){
        binding.apply {
            viewSearchEmpty.root.visibility = if (isShowEmpty) View.VISIBLE else View.GONE
            viewSearchNotFound.root.visibility = if (isShowEmpty) View.GONE else View.GONE
            errorLayout.root.visibility = if (isShowEmpty) View.GONE else View.GONE
            progressBarSearch.visibility = if (isShowEmpty) View.GONE else View.GONE
        }
    }

    private fun setProgressBar(isLoading: Boolean){
        binding.apply {
            progressBarSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
            viewSearchNotFound.root.visibility = if (isLoading) View.GONE else View.GONE
            rvSearch.visibility = if (isLoading) View.GONE else View.VISIBLE
            errorLayout.root.visibility = if (isLoading) View.GONE else View.GONE
            viewSearchEmpty.root.visibility = if (isLoading) View.GONE else View.GONE
        }
    }

    private fun setEmpty(isShowEmpty: Boolean){
        binding.apply {
            viewSearchEmpty.root.visibility = if (isShowEmpty) View.GONE else View.GONE
            viewSearchNotFound.root.visibility = if (isShowEmpty) View.VISIBLE else View.GONE
            viewSearchNotFound.textErrorNotFound.text = getString(R.string.search_not_found)
            errorLayout.root.visibility = if (isShowEmpty) View.GONE else View.GONE
            progressBarSearch.visibility = if (isShowEmpty) View.GONE else View.GONE
        }
    }

    private fun onClick(movie: Movie){
        Intent(requireActivity(),DetailActivity::class.java).apply {
            putExtra(Constant.POSITION,1)
            putExtra(Constant.EXTRA_MOVIE,movie)
        }.also { startActivity(it) }
    }

    private fun setFailed(isFailed: Boolean){
        binding.apply {
            viewSearchNotFound.root.visibility = if (isFailed) View.GONE else View.GONE
            rvSearch.visibility = if (isFailed) View.GONE else View.GONE
            progressBarSearch.visibility = if(isFailed) View.GONE else View.GONE
            errorLayout.root.visibility = if (isFailed) View.VISIBLE else View.GONE
            viewSearchEmpty.root.visibility = if (isFailed) View.GONE else View.GONE
            errorLayout.mtvEmptyText.text = getString(R.string.error)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}