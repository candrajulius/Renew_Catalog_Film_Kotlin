
package com.candra.katalog_film.core.utils

import android.content.Context
import com.candra.katalog_film.R
import java.text.SimpleDateFormat
import java.util.*

object FormatContent {

    fun formatContentMovieAdded(context: Context, title: String): String {
        val contentString = context.getString(R.string.insertToMovie)
        return String.format(contentString, title)
    }

    fun formatContentTvShowAdded(context: Context, title: String): String {
        val contentString = context.getString(R.string.insertToTvShow)
        return String.format(contentString, title)
    }

    fun String.formatContentMovieRemove(context: Context): String {
        val contentString = context.getString(R.string.deleteMovie)
        return String.format(contentString, this)
    }

    fun String.formatContentTvShowRemove(context: Context): String {
        val contentString = context.getString(R.string.deleteTvShow)
        return String.format(contentString, this)
    }



    fun String.formatContentDetail(context: Context,position: Int): String{
        var actualContent = ""
        if (position == 1){
            val contentStringDetailMovieTempt = context.getString(R.string.detail_movie)
            actualContent = String.format(contentStringDetailMovieTempt,this)
        }else if (position == 2){
            val contentStringDetailTvShowTempt = context.getString(R.string.detail_tv_show)
            actualContent = String.format(contentStringDetailTvShowTempt,this)
        }
        return actualContent
    }

    fun parsingDateFormat(releaseDate: String): String{
        val apiFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val simpelDateFormat = apiFormat.parse(releaseDate)
        val dateFormat = SimpleDateFormat("EEEE, dd-MMMM-yyyy",Locale.getDefault())
        return dateFormat.format(simpelDateFormat!!)
    }

}