package com.candra.katalog_film.core.ui.adapter.peopleadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.katalog_film.core.domain.model.Crew
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Helper.contentImageForCrewAndCast
import com.candra.katalog_film.databinding.ItemPeopleLayoutBinding

class CrewAdapter(
    private val onClick: (Crew) -> Unit
): RecyclerView.Adapter<CrewAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemPeopleLayoutBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(crew: Crew){
            with(binding){
                crew.apply {
                    gambar.contentImageForCrewAndCast(Constant.IMAGE_PATH2 + profilePath,itemView.context)
                    namaCrew.text = name
                    characterCrew.text = job
                }
                gambar.setOnClickListener {
                    onClick(crew)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewAdapter.ViewHolder =
        ViewHolder(
            ItemPeopleLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    override fun onBindViewHolder(holder: CrewAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<Crew>(){
        override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem == newItem
        }
    })

    fun submitListData(listData: List<Crew>){
        differ.submitList(listData)
    }

}