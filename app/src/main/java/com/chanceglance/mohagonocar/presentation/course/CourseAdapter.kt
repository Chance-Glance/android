package com.chanceglance.mohagonocar.presentation.course

import android.mtp.MtpEvent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.databinding.ItemCourseBinding

class CourseAdapter:RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    private val courseList:MutableList<Course> = mutableListOf()

    inner class CourseViewHolder(
        private val binding:ItemCourseBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun onBind(item:Course){
            binding.tvName.text=item.name
            binding.tvLocation.text=item.location
            binding.ivImage.load(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemCount(): Int = courseList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.onBind(courseList[position])
    }

    fun getList(){
        val list = listOf(
            Course(name = "경남고성공룡세계엑스포", location = "경상남도 고성군 당항만로 1116 당항포관광지", image = R.drawable.course_dinosour_resize),
            Course(name="선인장페스티벌",location="경기도 고양시 일산동구 호수로 731 (장항동)", image = R.drawable.course_cactus_resize),
            Course(name="계룡군문화축제", location="충청남도 계룡시 신도안면 정장리 16", image=R.drawable.army_course_resize)
        )
        courseList.clear()
        courseList.addAll(list)
        Log.d("courseAdapter","courselist: ${courseList}")
        notifyDataSetChanged()
    }
}