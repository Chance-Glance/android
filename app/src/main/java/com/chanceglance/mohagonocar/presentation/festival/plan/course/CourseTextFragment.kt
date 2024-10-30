package com.chanceglance.mohagonocar.presentation.festival.plan.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import com.chanceglance.mohagonocar.databinding.FragmentCourseTextBinding
import com.chanceglance.mohagonocar.extension.CourseState
import com.chanceglance.mohagonocar.presentation.festival.plan.nearby.NearbyViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CourseTextFragment:Fragment() {
    private var _binding:FragmentCourseTextBinding?=null
    private val binding:FragmentCourseTextBinding
        get()= requireNotNull(_binding){"null"}

    private lateinit var courseAdapter: CourseAdapter
    private var courseData: ResponseTravelCourseDto? = null
    private val courseViewModel:CourseViewModel by viewModels()
    private val nearbyViewModel:NearbyViewModel by activityViewModels()

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

        nearbyViewModel.courseState.value?.let { courseState ->
            if (courseState is CourseState.Success) {
                val list = courseViewModel.replaceList(courseState.data)
                courseAdapter = CourseAdapter()
                binding.rvRoute.adapter = courseAdapter
                courseAdapter.setData(list)
            }
        }
        // JSON 문자열을 객체로 변환
       /* arguments?.getString("courseData")?.let { courseDataJson ->
            courseData = Json.decodeFromString(courseDataJson)
        }*/


       /* val list = courseViewModel.replaceList(courseData!!)

        courseAdapter = CourseAdapter()
        binding.rvRoute.adapter=courseAdapter
        courseAdapter.setData(list)*/

        // 데이터를 설정하고 갱신
        /*val data = listOf(
            CourseItem.Place(name = "여행 장소 1"),  // 시작 장소
            CourseItem.SubPath(pathType = "BUS", startPlaceName = "여행 장소 1", endPlaceName = "버스 정류장1", sectionTime = 10),
            CourseItem.SubPath(pathType = "BUS", startPlaceName = "버스 정류장 1", endPlaceName = "여행 장소 2", sectionTime = 10),
            CourseItem.Place(name = "여행 장소 2"), // 중간 장소
            CourseItem.SubPath(pathType = "WALK", startPlaceName = "여행 장소 2", endPlaceName = "여행 장소 3", sectionTime = 15),
            CourseItem.EndPlace(name = "여행 장소 3")                // 마지막 장소
        )

        courseAdapter.setData(data)*/
    }
}