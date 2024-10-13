package com.chanceglance.mohagonocar.presentation.festival.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanceglance.mohagonocar.databinding.ItemPlaceBinding
import com.chanceglance.mohagonocar.presentation.festival.FestivalAdapter.FestivalViewHolder

class AddPlaceAdapter(private val onItemClicked: (String) -> Unit): RecyclerView.Adapter<AddPlaceAdapter.AddPlaceViewHolder>(){
    private val placeList:List<String> = listOf("구수민", "경기대학교", "전민주", "노태윤", "김송은",
        "김상후","구수민", "경기대학교", "전민주", "노태윤", "김송은", "김상후",)

    inner class AddPlaceViewHolder(
        private val binding:ItemPlaceBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun onBind(item:String){
            binding.tvName.text=item
            binding.root.setOnClickListener{
                onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPlaceViewHolder {
        val binding= ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return AddPlaceViewHolder(binding)
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: AddPlaceViewHolder, position: Int) {
        val item=placeList[position]
        holder.onBind(item)
    }
}