package com.chanceglance.mohagonocar.presentation.festival.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.chanceglance.mohagonocar.databinding.FragmentPlaceBinding
import java.util.Calendar
import kotlin.properties.Delegates

class PlaceFragment:Fragment() {
    private var _binding: FragmentPlaceBinding?= null
    private val binding: FragmentPlaceBinding
        get()= requireNotNull(_binding) {"null"}
    private val planViewModel:PlanViewModel by activityViewModels()
    private val placeViewModel:PlaceViewModel by viewModels()

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
        setListener(binding.svSearchDepart)
        setListener(binding.svSearchArrival)

        // ViewModel 데이터 관찰
        placeViewModel.decideDepart.observe(viewLifecycleOwner) { checkNextButtonState() }
        placeViewModel.decideArrival.observe(viewLifecycleOwner) { checkNextButtonState() }

        // 현재 시간으로 TimePicker 초기화
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // tp_depart와 tp_arrival 초기화
        binding.tpDepart.hour = currentHour
        binding.tpDepart.minute = currentMinute

        binding.tpArrival.hour = currentHour
        binding.tpArrival.minute = currentMinute

        // tp_depart에서 시간 변경 시 tp_arrival의 최소 시간을 설정
        binding.tpDepart.setOnTimeChangedListener { _, hourOfDay, minute ->
            updateArrivalTimePicker(hourOfDay, minute)
        }

        binding.btnSubmit.setOnClickListener{
            if(binding.btnSubmit.isSelected){
                (activity as PlanActivity).replaceFragment(AddPlaceFragment(), "AddPlaceFragment")
            }else {
                // 선택되지 않았다면 알림을 주는 로직
                Toast.makeText(requireContext(), "미선택된 항목이 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setListener(svSearch:SearchView) {
        // SearchView에서 생성되는 타자에 있는 돋보기 버튼 활성화
        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(svSearch==binding.svSearchDepart) placeViewModel.getDepart(true)
                else placeViewModel.getArrival(true)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }

    // 두 값이 모두 true인지 확인하는 함수
    private fun checkNextButtonState() {
        val isDepartDecided = placeViewModel.decideDepart.value ?: false
        val isArrivalDecided = placeViewModel.decideArrival.value ?: false
        binding.btnSubmit.isSelected = isDepartDecided && isArrivalDecided
    }

    private fun updateArrivalTimePicker(departHour: Int, departMinute: Int) {
        // tp_arrival의 시간을 tp_depart 이후로 설정
        binding.tpArrival.setIs24HourView(true)

        // tp_depart 이후의 시간만 선택 가능하게 설정
        if (binding.tpArrival.hour < departHour || (binding.tpArrival.hour == departHour && binding.tpArrival.minute <= departMinute)) {
            binding.tpArrival.hour = departHour
            binding.tpArrival.minute = departMinute + 1 // 최소 1분 이후로 설정
        }
    }
}