package com.candra.katalog_film.core.ui.adapter.traileradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.katalog_film.core.domain.model.Trailer
import com.candra.katalog_film.databinding.ItemTrailerLayoutBinding

class TrailerAdapter(
    private val onClickData: (Trailer) -> Unit
): RecyclerView.Adapter<TrailerAdapter.ViewHolder>(){

    inner class ViewHolder(
        private val binding: ItemTrailerLayoutBinding
    ): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(trailer: Trailer){
            with(binding){
                btnTrailer.text = trailer.name
                btnTrailer.setOnClickListener {
                    onClickData(trailer)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerAdapter.ViewHolder =
        ViewHolder(
            ItemTrailerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    override fun onBindViewHolder(holder: TrailerAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Trailer>(){
        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
            oldItem == newItem
    })

    fun submitListData(trailer: List<Trailer>){
        differ.submitList(trailer)
    }

}