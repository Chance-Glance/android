package com.chanceglance.mohagonocar.presentation.festival

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanceglance.mohagonocar.databinding.ItemFestivalBinding

class FestivalAdapter:RecyclerView.Adapter<FestivalAdapter.FestivalViewHolder>() {
    private val festivalList:List<String> = listOf("구수민", "경기대학교", "전민주", "노태윤", "김송은",
    "김상후","구수민", "경기대학교", "전민주", "노태윤", "김송은", "김상후",)
    inner class FestivalViewHolder(
        private val binding:ItemFestivalBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun onBind(item:String){
            binding.tvName.text=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalViewHolder {
        val binding=ItemFestivalBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FestivalViewHolder(binding)
    }

    override fun getItemCount(): Int = festivalList.size

    override fun onBindViewHolder(holder: FestivalViewHolder, position: Int) {
        val item=festivalList[position]
        holder.onBind(item)
    }
}