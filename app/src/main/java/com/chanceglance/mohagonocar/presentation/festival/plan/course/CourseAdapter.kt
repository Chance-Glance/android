package com.chanceglance.mohagonocar.presentation.festival.plan.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.databinding.ItemPlanPlaceBinding
import com.chanceglance.mohagonocar.databinding.ItemPlanRouteBinding

class CourseAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<CourseItem>()

    companion object {
        const val TYPE_PLACE = 0
        const val TYPE_SUBPATH = 1
        const val TYPE_END_PLACE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is CourseItem.Place -> TYPE_PLACE
            is CourseItem.SubPath -> TYPE_SUBPATH
            is CourseItem.EndPlace -> TYPE_END_PLACE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PLACE -> {
                val binding = ItemPlanPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlaceViewHolder(binding)
            }
            TYPE_SUBPATH -> {
                val binding = ItemPlanRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SubPathViewHolder(binding)
            }
            TYPE_END_PLACE -> {
                val binding = ItemPlanPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EndPlaceViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlaceViewHolder -> holder.bind(itemList[position] as CourseItem.Place)
            is SubPathViewHolder -> holder.bind(itemList[position] as CourseItem.SubPath)
            is EndPlaceViewHolder -> holder.bind(itemList[position] as CourseItem.EndPlace)
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class PlaceViewHolder(private val binding: ItemPlanPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CourseItem.Place) {
            binding.tvPlaceName.text = item.name
        }
    }

    inner class SubPathViewHolder(private val binding: ItemPlanRouteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CourseItem.SubPath) {
            if(item.pathType=="BUS"){
                binding.ivTypeIcon.load(R.drawable.ic_bus_black_24)
            }else if(item.pathType=="WALK"){
                binding.ivTypeIcon.load(R.drawable.ic_walk_black_24)
            }else{
                binding.ivTypeIcon.load(R.drawable.ic_subway_black_24)
            }
            //binding.tvRouteType.text = item.pathType
            binding.tvRouteStart.text = item.startPlaceName
            binding.tvRouteEnd.text = item.endPlaceName
            binding.tvRouteTime.text = "${item.sectionTime}분"
        }
    }

    inner class EndPlaceViewHolder(private val binding: ItemPlanPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CourseItem.EndPlace) {
            binding.tvPlaceName.text = item.name
        }
    }

    // 데이터를 설정하고 RecyclerView 갱신하는 메서드 추가
    fun setData(list: List<CourseItem>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

}
