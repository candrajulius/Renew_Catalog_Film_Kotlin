package com.candra.katalog_film.core.ui.adapter.movieadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.katalog_film.core.domain.model.Movie
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper.contentImage
import com.candra.katalog_film.databinding.ListItemHorizontalBinding

class MovieAdapter(
    private val onClick: (Movie) -> Unit
): RecyclerView.Adapter<MovieAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemHorizontalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int  = differ.currentList.size

    inner class ViewHolder(private val binding: ListItemHorizontalBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: Movie){
            with(binding){
                val url = Constant.IMAGE_PATH2 + data.thumbnail
                imagePhoto.contentImage(url,itemView.context)
                itemView.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    })

    fun temptDataAll(listData: List<Movie>){
        differ.submitList(listData)
    }

}