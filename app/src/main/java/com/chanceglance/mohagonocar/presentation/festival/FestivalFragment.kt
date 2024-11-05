package com.chanceglance.mohagonocar.presentation.festival

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.databinding.FragmentFestivalBinding
import com.chanceglance.mohagonocar.extension.FestivalState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class FestivalFragment: Fragment() {
    private var _binding:FragmentFestivalBinding ?= null
    private val binding:FragmentFestivalBinding
        get()= requireNotNull(_binding) {"null"}

    private lateinit var festivalAdapter:FestivalAdapter
    private val festivalViewModel:FestivalViewModel by viewModels()

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
        festivalViewModel.getFestival()
        lifecycleScope.launch {
            festivalViewModel.festivalState.collect{festivalState->
                when(festivalState){
                    is FestivalState.Success->{
                        festivalAdapter= FestivalAdapter{item ->
                            // 데이터를 JSON으로 변환
                            val festival:ResponseFestivalDto.Data.Item = item
                            val jsonString = Json.encodeToString(ResponseFestivalDto.Data.Item.serializer(),festival)

                            val intent = Intent(requireContext(), FestivalDetailActivity::class.java)
                            intent.putExtra("festivalItem", jsonString) // 클릭된 아이템 정보 전달
                            startActivity(intent)
                            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay)}

                        festivalAdapter.getList(festivalState.data.data.items)
                        binding.rvFestivals.adapter=festivalAdapter
                    }
                    is FestivalState.Loading->{}
                    is FestivalState.Error->{
                        Log.e("festivalFragment", "festival 가져오기 에러! - ${festivalState.message}")
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}