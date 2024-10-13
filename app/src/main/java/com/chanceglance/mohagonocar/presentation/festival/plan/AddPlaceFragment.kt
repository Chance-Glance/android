package com.chanceglance.mohagonocar.presentation.festival.plan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chanceglance.mohagonocar.databinding.FragmentAddPlaceBinding

class AddPlaceFragment:Fragment() {
    private var _binding: FragmentAddPlaceBinding?= null
    private val binding: FragmentAddPlaceBinding
        get()= requireNotNull(_binding) {"null"}
    private val planViewModel:PlanViewModel by activityViewModels()
    private lateinit var addPlaceAdapter: AddPlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAddPlaceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting(){
        addPlaceAdapter= AddPlaceAdapter { item ->
            val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
            intent.putExtra("festivalName", item) // 클릭된 아이템 정보 전달
            startActivity(intent)}
            binding.rvPlaces.adapter=addPlaceAdapter
        }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}