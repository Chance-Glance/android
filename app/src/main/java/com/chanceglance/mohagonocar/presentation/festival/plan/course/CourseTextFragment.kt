package com.chanceglance.mohagonocar.presentation.festival.plan.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chanceglance.mohagonocar.databinding.FragmentCourseTextBinding

class CourseTextFragment:Fragment() {
    private var _binding:FragmentCourseTextBinding?=null
    private val binding:FragmentCourseTextBinding
        get()= requireNotNull(_binding){"null"}
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCourseTextBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting(){
        Log.d("coursetextfragment", "Course Text Fragment Visible")
        courseAdapter = CourseAdapter()
        binding.rvRoute.adapter=courseAdapter

        // 데이터를 설정하고 갱신
        val data = listOf(
            CourseItem.Place(name = "여행 장소 1"),  // 시작 장소
            CourseItem.SubPath(pathType = "BUS", startPlaceName = "여행 장소 1", endPlaceName = "버스 정류장1", sectionTime = 10),
            CourseItem.SubPath(pathType = "BUS", startPlaceName = "버스 정류장 1", endPlaceName = "여행 장소 2", sectionTime = 10),
            CourseItem.Place(name = "여행 장소 2"), // 중간 장소
            CourseItem.SubPath(pathType = "WALK", startPlaceName = "여행 장소 2", endPlaceName = "여행 장소 3", sectionTime = 15),
            CourseItem.EndPlace(name = "여행 장소 3")                // 마지막 장소
        )

        courseAdapter.setData(data)
    }
}