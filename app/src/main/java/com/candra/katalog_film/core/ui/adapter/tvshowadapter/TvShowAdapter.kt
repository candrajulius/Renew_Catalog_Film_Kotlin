package com.candra.katalog_film.core.ui.adapter.tvshowadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.candra.katalog_film.R
import com.candra.katalog_film.core.domain.model.TvShow
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper.contentImage
import com.candra.katalog_film.databinding.ListItemHorizontalBinding


class TvShowAdapter(
    private val onClick: (TvShow) -> Unit
): RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemHorizontalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dataTvShow: TvShow){
            with(binding){
                val url = Constant.IMAGE_PATH2 + dataTvShow.thumbnail
                imagePhoto.contentImage(url,itemView.context)
                root.setOnClickListener {
                    onClick(dataTvShow)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemHorizontalBinding.inflate(LayoutInflater.from(parent.context),parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: TvShowAdapter.ViewHolder, position: Int) {
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

    fun temptAllDataTvShow(listTvShow: List<TvShow>){
        differ.submitList(listTvShow)
    }
}