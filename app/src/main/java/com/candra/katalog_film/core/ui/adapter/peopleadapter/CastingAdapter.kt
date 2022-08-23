package com.candra.katalog_film.core.ui.adapter.peopleadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.katalog_film.core.domain.model.Casting
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper.contentImageForCrewAndCast
import com.candra.katalog_film.databinding.ItemPeopleLayoutBinding

class CastingAdapter(
    private val onClickCasting: (Casting) -> Unit
): RecyclerView.Adapter<CastingAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemPeopleLayoutBinding): RecyclerView.ViewHolder(
        binding.root
    ){
        fun bind(casting: Casting){
            with(binding){
                casting.apply {
                    gambar.contentImageForCrewAndCast(Constant.IMAGE_PATH2 + profilePath,itemView.context)
                    namaCrew.text = name
                    characterCrew.text = character
                }
                gambar.setOnClickListener {
                    onClickCasting(casting)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastingAdapter.ViewHolder =
        ViewHolder(
            ItemPeopleLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    override fun onBindViewHolder(holder: CastingAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Casting>(){
        override fun areItemsTheSame(oldItem: Casting, newItem: Casting): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Casting, newItem: Casting): Boolean {
            return oldItem == newItem
        }
    })

    fun submitListData(listData: List<Casting>){
        differ.submitList(listData)
    }
}