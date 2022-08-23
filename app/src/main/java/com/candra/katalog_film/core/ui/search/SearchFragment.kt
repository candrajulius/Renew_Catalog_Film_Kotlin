package com.candra.katalog_film.core.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.candra.katalog_film.R
import com.candra.katalog_film.core.ui.adapter.ViewPagerAdapter
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.SearchLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator



class SearchFragment: Fragment()
{
    private var _binding: SearchLayoutBinding? = null

    companion object{
        @StringRes
        private val TAB_TTILES = intArrayOf(
            R.string.movie,
            R.string.tv_show
        )
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSectionPagerAdapter()
    }

    private fun setSectionPagerAdapter(){
        val sectionPagerAdapter = ViewPagerAdapter(requireActivity())
        binding.apply {
            viewPagerSearch.adapter = sectionPagerAdapter
            TabLayoutMediator(tabSearch,viewPagerSearch){tab,position ->
                tab.text = resources.getString(TAB_TTILES[position])
            }.attach()
            tabSearch.setSelectedTabIndicatorColor(
                if(requireActivity().isDarkMode) ContextCompat.getColor(requireActivity(),R.color.white)
                else ContextCompat.getColor(requireActivity(),R.color.black)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}