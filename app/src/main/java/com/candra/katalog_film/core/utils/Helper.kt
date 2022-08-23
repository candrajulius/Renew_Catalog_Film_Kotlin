package com.candra.katalog_film.core.utils

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.candra.katalog_film.R
import com.candra.katalog_film.core.data.source.local.entity.ModelEntity
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.domain.model.TvShow

object Helper {

    fun mapEntitiesToDomainMovie(input: List<ModelEntity>):List<Movie> {
        return input.map {
            Movie(
                id = it.id,
                title = it.title,
                voteAverage = it.voteAverage,
                releaseData = it.releaseData,
                popularity = it.popularity,
                overview = it.overview,
                thumbnail = it.thumbnail,
                cover = it.cover,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapDomainToEntityMovie(input: Movie) = ModelEntity(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        releaseData = input.releaseData,
        popularity = input.popularity,
        overview = input.overview,
        cover = input.cover,
        thumbnail = input.thumbnail,
        isFavorite = input.isFavorite
    )

    fun mapEntitiesToDomainTvShow(input: List<ModelEntity>): List<TvShow>{
        return input.map {
            TvShow(
                id = it.id,
                title = it.title,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseData,
                popularity = it.popularity,
                overview = it.overview,
                thumbnail = it.thumbnail,
                cover = it.cover,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapDomainToEntityTvShow(input: TvShow) = ModelEntity(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        releaseData = input.releaseDate,
        popularity = input.popularity,
        overview = input.overview,
        cover = input.cover,
        thumbnail = input.thumbnail,
        isFavorite = input.isFavorite
    )

    val Context.isDarkMode get() = this.resources?.configuration?.uiMode?.and(UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES

    fun constLinearLayoutManager(context: Context,position: Int): LinearLayoutManager? {
        var layoutManager: LinearLayoutManager? = null
        when(position){
            1 -> layoutManager =  LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            2 -> layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            3 -> layoutManager = GridLayoutManager(context,2)
        }
        return layoutManager
    }

    fun ImageView.contentImage(url: String,context: Context){
        this.load(url){
            crossfade(600)
            crossfade(true)
            if (context.isDarkMode){
                error(R.drawable.ic_baseline_broken_image_24_white)
                placeholder(R.drawable.ic_baseline_image_24_white)
            }else{
                error(R.drawable.ic_baseline_broken_image_24)
                placeholder(R.drawable.ic_baseline_image_24)
            }
        }
    }

    fun ImageView.contentImageForCrewAndCast(url: String,context: Context){
        this.load(url){
            crossfade(600)
            crossfade(true)
            if (context.isDarkMode){
                error(R.drawable.ic_baseline_broken_image_24_white)
                placeholder(R.drawable.ic_baseline_image_24_white)
            }else{
                error(R.drawable.ic_baseline_broken_image_24)
                placeholder(R.drawable.ic_baseline_image_24)
            }
            transformations(CircleCropTransformation())
        }
    }

    fun showProgressBar(isShow: Boolean,progressBar: ProgressBar,recyclerView: RecyclerView){
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    fun Context.makeToast(title: String){
        Toast.makeText(this,title,Toast.LENGTH_SHORT).show()
    }

}