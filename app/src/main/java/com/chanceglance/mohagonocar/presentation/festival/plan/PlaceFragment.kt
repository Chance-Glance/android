package com.chanceglance.mohagonocar.presentation.festival.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chanceglance.mohagonocar.databinding.FragmentPlaceBinding

class PlaceFragment:Fragment() {
    private var _binding: FragmentPlaceBinding?= null
    private val binding: FragmentPlaceBinding
        get()= requireNotNull(_binding) {"null"}
    private val planViewModel:PlanViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPlaceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting(){

    }

}