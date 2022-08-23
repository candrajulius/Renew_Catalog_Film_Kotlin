package com.candra.katalog_film.core.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.candra.katalog_film.core.ui.search_fragment.MovieSearchFragment
import com.candra.katalog_film.core.ui.search_fragment.TvShowSearchFragment

class ViewPagerAdapter(
    fm: FragmentActivity
): FragmentStateAdapter(fm)
{
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = TvShowSearchFragment()
            1 -> fragment = MovieSearchFragment()
        }
        return fragment as Fragment
    }
    
}