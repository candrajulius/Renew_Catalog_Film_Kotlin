package com.candra.katalog_film.core.ui.adapter.movieadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.katalog_film.R
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.FormatContent.parsingDateFormat
import com.candra.katalog_film.core.utils.Helper.contentImage
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.ListItemRecomendedBinding

class RecomendedMovieAdapter(
    private val onClick: (Movie) -> Unit,
): RecyclerView.Adapter<RecomendedMovieAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ListItemRecomendedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(
        private val binding: ListItemRecomendedBinding
    ): RecyclerView.ViewHolder(binding.root)
    {
        fun bindData(data: Movie){
            with(binding){
                data.apply {
                    val url = Constant.IMAGE_PATH + thumbnail
                    imgPhoto.contentImage(url,itemView.context)
                    tvTitle.text = title
                    tvDesc.text = overview
                    if (data.releaseData == ""){
                        tvReleaseDate.text = itemView.context.resources.getString(R.string.data_is_null)
                    }else{
                        tvReleaseDate.text = parsingDateFormat(data.releaseData)
                    }
                    val newValueRating = voteAverage.toFloat()
                    ratingBar.apply {
                        numStars = 5
                        stepSize = 0.5F
                        rating = newValueRating / 2
                    }
                }
                rootFrameLayout.setOnClickListener {
                    onClick(data)
                }

                tvTitle.setTextColor(if (itemView.context.isDarkMode) ContextCompat.getColor(itemView.context,R.color.black)
                else ContextCompat.getColor(itemView.context,R.color.black))
                tvDesc.setTextColor(if (itemView.context.isDarkMode) ContextCompat.getColor(itemView.context,R.color.black)
                else ContextCompat.getColor(itemView.context,R.color.black))
            }
        }
    }

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    })

    fun temptAllData(listMovie: List<Movie>){
        differ.submitList(listMovie)
    }

}