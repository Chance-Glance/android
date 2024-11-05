package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.databinding.FragmentNearbyPlaceBinding
import com.chanceglance.mohagonocar.extension.CourseState
import com.chanceglance.mohagonocar.extension.NearbyPlaceState
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanActivity
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanActivity.Companion.REQUEST_CODE
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanViewModel
import com.chanceglance.mohagonocar.presentation.festival.plan.course.CourseWithFestivalFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Response

@AndroidEntryPoint
class NearbyPlaceFragment : Fragment() {
    private var _binding: FragmentNearbyPlaceBinding? = null
    private val binding: FragmentNearbyPlaceBinding
        get() = requireNotNull(_binding) { "null" }
    private val planViewModel: PlanViewModel by activityViewModels()
    private val nearbyViewModel: NearbyViewModel by activityViewModels()

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
        planViewModel.festivalId.observe(viewLifecycleOwner) { id ->
            nearbyViewModel.getNearbyPlace(id)

            lifecycleScope.launch {
                nearbyViewModel.nearbyPlaceState.collect { nearbyPlaceState ->
                    when (nearbyPlaceState) {
                        is NearbyPlaceState.Success -> {
                            nearbyPlaceAdapter = NearbyPlaceAdapter(
                                requireContext(),
                                clickButton = { placeItem ->
                                    selectButton(placeItem)
                                },
                                onItemClicked = { item ->
                                    val festival: ResponseNearbyPlaceDto.Data.Item = item
                                    val itemJsonString = Json.encodeToString(
                                        ResponseNearbyPlaceDto.Data.Item.serializer(),
                                        festival
                                    )

                                    // val list = ArrayList(getList()) // List를 ArrayList로 변환

                                    val intent =
                                        Intent(requireContext(), PlaceDetailActivity::class.java)
                                    intent.putExtra("placeItem", itemJsonString) // 클릭된 아이템 정보 전달
                                    intent.putExtra("isSelect", nearbyViewModel.isSelect(item))
                                    //intent.putExtra("selectList",list)
                                    placeDetailLauncher.launch(intent)
                                    requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
                                })

                            nearbyPlaceAdapter.getList(nearbyPlaceState.data.data.items)
                            binding.rvPlaces.adapter = nearbyPlaceAdapter
                        }

                        is NearbyPlaceState.Loading -> {}
                        is NearbyPlaceState.Error -> {
                            Log.e(
                                "nearbyPlaceFragment",
                                "nearby place 가져오기 에러! - ${nearbyPlaceState.message}"
                            )
                        }
                    }
                }
            }

            binding.btnSubmit.setOnClickListener {
                if (binding.btnSubmit.isSelected) {
                    val id = planViewModel.festivalId.value!!
                    val date = planViewModel.selectedDate.value!!
                    val depart = planViewModel.selectedDepartTime.value!!
                    val arrival = planViewModel.selectedArrivalTime.value!!
                    val placeList =
                        nearbyViewModel.selectPlaceList.value?.map { it.id } ?: emptyList()

                    nearbyViewModel.getCourse(id, date, depart, arrival, placeList)
                    lifecycleScope.launch {
                        nearbyViewModel.courseState.collect { courseState ->
                            when (courseState) {
                                is CourseState.Success -> {
                                    val fragment = CourseWithFestivalFragment()
                                    (activity as PlanActivity).replaceFragment(
                                        fragment,
                                        "CourseWithFestivalFragment"
                                    )
                                    /*val courseData = courseState.data // ResponseTravelCourseDto 객체를 가져옴

                                    // ResponseTravelCourseDto를 JSON 문자열로 변환
                                    val courseDataJson = Json.encodeToString(courseData)

                                    // Fragment에 전달할 Bundle 생성
                                    val fragment = CourseWithFestivalFragment().apply {
                                        arguments = Bundle().apply {
                                            putString("courseData", courseDataJson)
                                        }
                                    }

                                    // Fragment 교체
                                    (activity as PlanActivity).replaceFragment(fragment, "CourseWithFestivalFragment")
 */
                                }

                                is CourseState.Loading -> {}
                                is CourseState.Error -> {
                                    Log.e(
                                        "nearbyPlaceFragment",
                                        "courseState is error!-${courseState.message}"
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private fun selectButton(placeItem: ResponseNearbyPlaceDto.Data.Item) {
        val count = nearbyViewModel.selectNearbyPlace(placeItem)
        with(binding) {
            when (count) {
                1 -> {
                    btnSubmit.isSelected = true
                    btnSubmit.text = getString(R.string.plan_finish)
                    btnSubmit.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                0 -> {
                    btnSubmit.isSelected = false
                    btnSubmit.text = getString(R.string.plan_finish)
                    btnSubmit.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                -1 -> {
                    btnSubmit.isSelected = false
                    btnSubmit.text = getString(R.string.btn_max_error)
                    btnSubmit.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.plan_error_red
                        )
                    )
                }
            }
        }
    }

    private fun getList(): List<ResponseNearbyPlaceDto.Data.Item> {
        // 기본적으로 LiveData의 현재 값을 가져옴
        val placeList = mutableListOf<ResponseNearbyPlaceDto.Data.Item>()

        // LiveData에 값이 있는 경우 즉시 값을 추가
        nearbyViewModel.selectPlaceList.value?.let { list ->
            placeList.addAll(list)
        }

        // 변경 사항이 있을 때 추가적으로 업데이트
        nearbyViewModel.selectPlaceList.observe(viewLifecycleOwner) { list ->
            placeList.clear() // 기존 값을 지우고
            placeList.addAll(list) // 새로 업데이트된 값으로 채움
        }

        return placeList
    }

    private val placeDetailLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val itemJsonString = result.data?.getStringExtra("selectedItem")
                val item = itemJsonString?.let {
                    try {
                        Json.decodeFromString<ResponseNearbyPlaceDto.Data.Item>(it)
                    } catch (e: Exception) {
                        Log.e("NearbyPlaceFragment", "JSON decoding error: ${e.message}")
                        null
                    }
                }

                if (item != null) {
                    Log.d("NearbyPlaceFragment", "Item received: ${item.name}")
                    val isItemSelected =
                        result.data?.getBooleanExtra("isItemSelected", false) ?: false
                    updateItemSelection(item, isItemSelected)
                } else {
                    Log.e("NearbyPlaceFragment", "Item is null or failed to decode")
                }
            } else {
                Log.d("NearbyPlaceFragment", "Result not OK")
            }
        }

    private fun updateItemSelection(item: ResponseNearbyPlaceDto.Data.Item, isSelected: Boolean) {
        // _selectPlace의 현재 값을 가져와서 수정 가능한 리스트로 변환
        nearbyViewModel.selectPlaceList.value?.let { list ->
            Log.d("nearbyplacefragment", "isSelected: ${isSelected}")
            if (list.contains(item) && !isSelected || (!list.contains(item) && isSelected)) {
                Log.d("nearbyplacefragment", "item contain, delete it or not contain and add it")
                Log.d("nearbyplacefragment", "list: ${list}")
                nearbyPlaceAdapter.updateSelection(item, isSelected)
                selectButton(item)
            }
        } ?: run {
            Log.e("nearbyplacefragment", "selectPlaceList is null")
            if (isSelected) {
                Log.d("nearbyplacefragment", "add item")
                nearbyPlaceAdapter.updateSelection(item, isSelected)
                selectButton(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateAdapterSelectionFromViewModel()
    }

    private fun updateAdapterSelectionFromViewModel() {
        val selectedList = nearbyViewModel.selectPlaceList.value ?: return
        selectedList.forEach { item ->
            nearbyPlaceAdapter.updateSelection(item, true)
        }
        binding.btnSubmit.isSelected=true
        binding.btnSubmit.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}