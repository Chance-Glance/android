package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.databinding.FragmentNearbyPlaceBinding
import com.chanceglance.mohagonocar.extension.NearbyPlaceState
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class NearbyPlaceFragment : Fragment() {
    private var _binding: FragmentNearbyPlaceBinding? = null
    private val binding: FragmentNearbyPlaceBinding
        get() = requireNotNull(_binding) { "null" }
    private val planViewModel: PlanViewModel by activityViewModels()
    private val nearbyViewModel: NearbyViewModel by viewModels()
    private lateinit var nearbyPlaceAdapter: NearbyPlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearbyPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting() {
        planViewModel.festivalId.observe(viewLifecycleOwner){id->
            nearbyViewModel.getNearbyPlace(id)

            lifecycleScope.launch {
                nearbyViewModel.nearbyPlaceState.collect{nearbyPlaceState->
                    when(nearbyPlaceState){
                        is NearbyPlaceState.Success->{
                            nearbyPlaceAdapter = NearbyPlaceAdapter { item ->
                                val festival: ResponseNearbyPlaceDto.Data.Item = item
                                val itemJsonString = Json.encodeToString(ResponseNearbyPlaceDto.Data.Item.serializer(),festival)

                                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                                intent.putExtra("festivalName", itemJsonString) // 클릭된 아이템 정보 전달
                                startActivity(intent)
                            }
                            nearbyPlaceAdapter.getList(nearbyPlaceState.data.data.items)
                            binding.rvPlaces.adapter = nearbyPlaceAdapter
                        }
                        is NearbyPlaceState.Loading->{}
                        is NearbyPlaceState.Error->{
                            Log.e("nearbyPlaceFragment", "nearby place 가져오기 에러! - ${nearbyPlaceState.message}")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}