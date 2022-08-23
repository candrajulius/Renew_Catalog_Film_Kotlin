package com.candra.katalog_film.core.ui.mainutama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.candra.katalog_film.R
import com.candra.katalog_film.core.ui.movie.MovieFragment
import com.candra.katalog_film.core.ui.search.SearchFragment
import com.candra.katalog_film.core.ui.tvshow.TvShowFragment
import com.candra.katalog_film.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(MovieFragment(),supportFragmentManager)


        supportActionBar?.apply {
            title = resources.getString(R.string.name_developer)
            subtitle = resources.getString(R.string.app_name)
        }

        setBottomNavigation()
    }

    private fun setBottomNavigation(){
        binding.bottomNavigation.setOnItemSelectedListener {
            supportFragmentManager.apply {
                when(it.itemId){
                    R.id.movie ->loadFragment(MovieFragment(),this)
                    R.id.tv_show -> loadFragment(TvShowFragment(),this)
                    R.id.search -> loadFragment(SearchFragment(),this)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    fun loadFragment(fragment: Fragment, fragmentManager: FragmentManager){
        fragmentManager.beginTransaction().replace(R.id.container_fragment,fragment).commit()
    }

}