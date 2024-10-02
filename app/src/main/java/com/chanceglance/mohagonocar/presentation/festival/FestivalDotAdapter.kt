package com.chanceglance.mohagonocar.presentation.festival

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.chanceglance.mohagonocar.R

class FestivalDotAdapter(var context:Context):PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view :View?= null
        var inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.pager_adapter, container, false)
        var imageView = view.findViewById<ImageView>(R.id.iv_image)

        if(position==0){
            imageView.setBackgroundColor((Color.parseColor("#bdbdbd")))
        }else if(position==1){
            imageView.setBackgroundColor((Color.parseColor("#FF0000")))
        }else if(position==2){
            imageView.setBackgroundColor((Color.parseColor("#1DDB16")))
        }else{
            imageView.setBackgroundColor((Color.parseColor("#F361DC")))
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
        return 4
    }
}