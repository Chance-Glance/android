package com.chanceglance.mohagonocar.presentation.festival

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.databinding.ItemFestivalBinding

class FestivalAdapter(private val onItemClicked: (ResponseFestivalDto.Data.Item) -> Unit):RecyclerView.Adapter<FestivalAdapter.FestivalViewHolder>() {
    /*private val festivalList:List<String> = listOf("구수민", "경기대학교", "전민주", "노태윤", "김송은",
    "김상후","구수민", "경기대학교", "전민주", "노태윤", "김송은", "김상후",)*/
    private val festivalList:MutableList<ResponseFestivalDto.Data.Item> = mutableListOf()

    inner class FestivalViewHolder(
        private val binding:ItemFestivalBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun onBind(item: ResponseFestivalDto.Data.Item){
            binding.tvName.text=item.name
            if (item.imageUrlList.isNotEmpty()) {
                val url = item.imageUrlList[0]

                // Coil을 사용하여 ConstraintLayout 배경 이미지 설정
                val request = ImageRequest.Builder(binding.itemFestival.context)
                    .data(url)
                    .transformations(RoundedCornersTransformation(150f)) // 모서리 반지름 30으로 설정
                    .target { drawable ->
                        // 배경 이미지를 로드한 후 ColorFilter를 적용해 어둡게 설정
                        drawable.colorFilter = PorterDuffColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.SRC_ATOP)    //porterduff.mode.src_atop : 원본 이미지에 덧씌우기
                        binding.itemFestival.background = drawable
                    }
                    .build()

                // 이미지 요청 실행
                binding.itemFestival.context.imageLoader.enqueue(request)
            }
            binding.tvLocation.text=item.address

            val startDate = item.activePeriod.startDate.toString()
            val endDate = item.activePeriod.endDate.toString()
            binding.tvDate.text= binding.itemFestival.context.getString(R.string.festival_duration, startDate,endDate)

            binding.root.setOnClickListener {
                onItemClicked(item) // 아이템 클릭 시 콜백 호출
            }
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

    fun getList(newList:List<ResponseFestivalDto.Data.Item>){
        festivalList.clear()
        festivalList.addAll(newList)
        notifyDataSetChanged()
    }
}