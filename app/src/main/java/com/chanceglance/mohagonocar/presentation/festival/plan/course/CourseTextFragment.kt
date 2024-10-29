package com.chanceglance.mohagonocar.presentation.festival.plan.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chanceglance.mohagonocar.databinding.FragmentCourseTextBinding

class CourseTextFragment:Fragment() {
    private var _binding:FragmentCourseTextBinding?=null
    private val binding:FragmentCourseTextBinding
        get()= requireNotNull(_binding){"null"}

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

    }
}