package com.candra.katalog_film.core.ui.adapter.genreadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.katalog_film.core.data.source.remote.response.detailresponse.Genre
import com.candra.katalog_film.core.domain.model.Genres
import com.candra.katalog_film.databinding.GenreLayoutBinding

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: GenreLayoutBinding):
    RecyclerView.ViewHolder(binding.root)
    {
        fun bind(genre: Genres){
            with(binding){
                genre.apply {
                    if (id == 0 && name.isEmpty()){
                        btnGenre.text = name
                    }else{
                        btnGenre.text = name
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
       return ViewHolder(
           GenreLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Genres>(){
        override fun areItemsTheSame(oldItem: Genres, newItem: Genres): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genres, newItem: Genres): Boolean {
            return oldItem == newItem
        }

    })

    fun submitListDataGenre(listData: List<Genres>){
        differ.submitList(listData)
    }

    override fun getItemCount(): Int = differ.currentList.size

}