package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.databinding.ItemPlaceBinding

class NearbyPlaceAdapter(private val onItemClicked: (ResponseNearbyPlaceDto.Data.Item) -> Unit): RecyclerView.Adapter<NearbyPlaceAdapter.AddPlaceViewHolder>(){
    /*private val placeList:List<String> = listOf("구수민", "경기대학교", "전민주", "노태윤", "김송은",
        "김상후","구수민", "경기대학교", "전민주", "노태윤", "김송은", "김상후",)*/

    private val placeList:MutableList<ResponseNearbyPlaceDto.Data.Item> = mutableListOf()

    inner class AddPlaceViewHolder(
        private val binding:ItemPlaceBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun onBind(item:ResponseNearbyPlaceDto.Data.Item){
            Log.d("ImageList", "imageUrlList size: ${item.imageUrlList.size}")
            Log.d("ImageList", "First image URL: ${item.imageUrlList.getOrNull(0)}")

            with(binding){
                tvName.text=item.name
                tvLocation.text=item.address
                ivImage.load(item.imageUrlList.getOrNull(0) ?: R.drawable.cat) {
                    transformations(RoundedCornersTransformation(30f)) // 30f로 모서리 둥글게
                    error(R.drawable.cat) // 로드 실패 시 기본 이미지
                }
                root.setOnClickListener{
                    onItemClicked(item)
                }
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

    fun getList(list:List<ResponseNearbyPlaceDto.Data.Item>){
        placeList.clear()
        placeList.addAll(list)
        notifyDataSetChanged()
    }
}