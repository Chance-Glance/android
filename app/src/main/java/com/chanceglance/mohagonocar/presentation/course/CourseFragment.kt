package com.chanceglance.mohagonocar.presentation.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import com.chanceglance.mohagonocar.databinding.FragmentCourseBinding
import com.chanceglance.mohagonocar.databinding.FragmentCourseTextBinding
import com.chanceglance.mohagonocar.presentation.festival.plan.course.CourseAdapter
import com.chanceglance.mohagonocar.presentation.festival.plan.course.CourseViewModel
import com.chanceglance.mohagonocar.presentation.festival.plan.nearby.NearbyViewModel

class CourseFragment:Fragment() {
    private var _binding: FragmentCourseBinding?=null
    private val binding: FragmentCourseBinding
        get()= requireNotNull(_binding){"null"}

    private lateinit var courseAdapter: com.chanceglance.mohagonocar.presentation.course.CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCourseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting(){
        courseAdapter= com.chanceglance.mohagonocar.presentation.course.CourseAdapter()
        binding.rvCourse.adapter=courseAdapter
        courseAdapter.getList()
    }
}