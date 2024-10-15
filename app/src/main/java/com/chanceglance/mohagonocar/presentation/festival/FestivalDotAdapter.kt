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

class FestivalDotAdapter(var context:Context):PagerAdapter() {

    private val imageList:MutableList<String> = mutableListOf()

    fun getImageList(newImages:List<String>){
        imageList.clear()
        imageList.addAll(newImages)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("ImageLoad", "instantiateItem called for position: $position")
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pager_adapter, container, false)
        val imageView = view.findViewById<ImageView>(R.id.iv_image)

        // 이미지 리스트가 비어 있으면 기본 이미지를 로드
        if (imageList == null || imageList.isEmpty()) {
            //Log.d("festivalDotAdapter", "Image list is null or empty, setting default image")
            imageView.setImageResource(R.drawable.cat) // 기본 이미지 설정
            Log.e("festivalDotAdapter", "Image list is null or empty, setting default image")

        } else {
            // imageUrlList에서 position에 해당하는 이미지를 불러와 설정
            val imageUrl = imageList[position % imageList.size] // 이미지를 순환해서 사용
            //Log.d("festivalDotAdapter", "Loading image from URL: $imageUrl")
            imageView.load(imageUrl) {
                placeholder(R.drawable.cat) // 이미지가 로드되는 동안 보여줄 플레이스홀더 이미지
                error(R.drawable.error) // 오류가 발생할 경우 보여줄 이미지
            }
        }


        container.addView(view)
        return view
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