package com.chanceglance.mohagonocar.presentation.festival

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import coil.load
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.databinding.PagerAdapterBinding

class FestivalDotAdapter(var context:Context):PagerAdapter() {

    private val imageList:MutableList<String> = mutableListOf()

    fun getImageList(newImages:List<String>){
        imageList.clear()
        imageList.addAll(newImages)
        notifyDataSetChanged()
    }

    /*override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("ImageLoad", "instantiateItem called for position: $position")
        val binding = PagerAdapterBinding.inflate(LayoutInflater.from(context), container, false)

        // 초기 설정: Shimmer 보이기, 이미지 숨기기
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.ivImage.visibility = View.GONE
        binding.shimmerLayout.startShimmer()

        if (imageList.isEmpty()) {
            // 이미지 리스트가 비어 있을 경우 기본 이미지를 로드하고 Shimmer 중지
            binding.ivImage.setImageResource(R.drawable.cat)
            binding.ivImage.visibility = View.VISIBLE
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
        } else {
            // imageList에서 position에 해당하는 이미지를 로드
            val imageUrl = imageList[position % imageList.size]
            binding.ivImage.load(imageUrl) {
                placeholder(R.drawable.cat) // 로딩 중일 때 보여줄 플레이스홀더 이미지
                error(R.drawable.error) // 로딩 실패 시 보여줄 이미지
                listener(
                    onSuccess = { _, _ ->
                        // 이미지 로딩 성공 시 Shimmer 중지 및 숨김 처리
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.ivImage.visibility = View.VISIBLE
                    },
                    onError = { _, _ ->
                        // 이미지 로딩 실패 시 Shimmer 중지 및 오류 이미지 표시
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.ivImage.setImageResource(R.drawable.error)
                        binding.ivImage.visibility = View.VISIBLE
                    }
                )
            }
        }

        container.addView(binding.root)
        return binding.root
    }*/



    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("ImageLoad", "instantiateItem called for position: $position")
        val binding = PagerAdapterBinding.inflate(LayoutInflater.from(context), container, false)

        if (imageList.isEmpty()) {
            // 이미지 리스트가 비어 있으면 기본 이미지를 로드
            binding.ivImage.setImageResource(R.drawable.cat)
            Log.e("festivalDotAdapter", "Image list is empty, setting default image")
        } else {
            // imageList에서 position에 해당하는 이미지를 불러와 설정
            val imageUrl = imageList[position % imageList.size] // 이미지를 순환해서 사용
            binding.ivImage.load(imageUrl) {
                placeholder(R.drawable.cat) // 로딩 중일 때 플레이스홀더 이미지
                error(R.drawable.error) // 오류가 발생할 경우 이미지
            }
        }

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return if (imageList.isEmpty()) {
            1 // 이미지가 없을 때도 1을 반환해서 기본 이미지를 보여줌
        } else {
            imageList.size
        }
    }
}