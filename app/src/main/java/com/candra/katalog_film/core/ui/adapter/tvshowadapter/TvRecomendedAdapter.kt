package com.candra.katalog_film.core.ui.adapter.tvshowadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.candra.katalog_film.R
import com.candra.katalog_film.core.domain.model.TvShow
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.FormatContent.parsingDateFormat
import com.candra.katalog_film.core.utils.Helper.contentImage
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.ListItemRecomendedBinding

class TvRecomendedAdapter(
    private val onClickTvRecomended: (TvShow) -> Unit
): RecyclerView.Adapter<TvRecomendedAdapter.ViewHolder>()
{

    inner class ViewHolder(private val binding: ListItemRecomendedBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(dataShowTv: TvShow){
            with(binding){
                tvTitle.text = dataShowTv.title
                if (dataShowTv.releaseDate == ""){
                    tvReleaseDate.text = itemView.context.resources.getString(R.string.data_is_null)
                }else{
                    tvReleaseDate.text = parsingDateFormat(dataShowTv.releaseDate)
                }
                tvDesc.text = dataShowTv.overview
                val newValueRating = dataShowTv.voteAverage.toFloat()
                val urlImage = Constant.IMAGE_PATH2 + dataShowTv.thumbnail
                imgPhoto.contentImage(urlImage,itemView.context)

                ratingBar.apply {
                    numStars = 5
                    stepSize = 0.5F
                    rating = newValueRating / 2
                }
                rootFrameLayout.setOnClickListener {
                    onClickTvRecomended(dataShowTv)
                }
                tvTitle.setTextColor(if (itemView.context.isDarkMode) ContextCompat.getColor(itemView.context,
                    R.color.black)
                else ContextCompat.getColor(itemView.context, R.color.black))
                tvDesc.setTextColor(if (itemView.context.isDarkMode) ContextCompat.getColor(itemView.context,
                    R.color.black)
                else ContextCompat.getColor(itemView.context, R.color.black))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvRecomendedAdapter.ViewHolder {
        return ViewHolder(
            ListItemRecomendedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: TvRecomendedAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<TvShow>(){
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem == newItem
        }
    })

    fun temptAllDataTvShowRecomended(listItem: List<TvShow>){
        differ.submitList(listItem)
    }

}