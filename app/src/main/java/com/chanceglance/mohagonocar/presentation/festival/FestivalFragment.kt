package com.chanceglance.mohagonocar.presentation.festival

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chanceglance.mohagonocar.databinding.FragmentFestivalBinding

class FestivalFragment: Fragment() {
    private var _binding:FragmentFestivalBinding ?= null
    private val binding:FragmentFestivalBinding
        get()= requireNotNull(_binding) {"null"}

    private lateinit var festivalAdapter:FestivalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentFestivalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFestivalList()

    }

    private fun getFestivalList(){
        festivalAdapter= FestivalAdapter{item ->
        val intent = Intent(requireContext(), FestivalDetailActivity::class.java)
        intent.putExtra("festivalName", item) // 클릭된 아이템 정보 전달
        startActivity(intent)}
        binding.rvFestivals.adapter=festivalAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}