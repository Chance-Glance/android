package com.chanceglance.mohagonocar.presentation.festival.plan.time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanceglance.mohagonocar.databinding.FragmentTimeBinding
import com.chanceglance.mohagonocar.presentation.festival.plan.AddPlaceFragment
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanActivity
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanViewModel

class TimeFragment:Fragment() {
    private var _binding: FragmentTimeBinding?= null
    private val binding: FragmentTimeBinding
        get()= requireNotNull(_binding) {"null"}
    private val planViewModel: PlanViewModel by activityViewModels()
    private val timeViewModel: TimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentTimeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting() {
        setListener(binding.svSearchDepart)
        setListener(binding.svSearchArrival)

        // ViewModel 데이터 관찰
        timeViewModel.decideDepart.observe(viewLifecycleOwner) { checkNextButtonState() }
        timeViewModel.decideArrival.observe(viewLifecycleOwner) { checkNextButtonState() }

        // 시간 리스트 생성 (00:00 ~ 23:59)
        val times = generateTimes()

        // 출발 시간 RecyclerView 설정
        val departAdapter = TimeAdapter(times, { selectedTime ->
            val selectedHour = selectedTime.split(":")[0].toInt()
            val selectedMinute = selectedTime.split(":")[1].toInt()

            // 선택된 출발 시간을 저장
            //binding.rvDepart.text = "출발 시간: $selectedTime"
            updateArrivalRecyclerView(selectedHour, selectedMinute)
        }, { false }) // 출발 시간은 항상 활성화

        binding.rvDepart.adapter = departAdapter

        // 초기 도착 시간 설정
        updateArrivalRecyclerView(0, 0) // 출발 시간보다 이후의 도착 시간만 표시

        binding.btnSubmit.setOnClickListener{
            if(binding.btnSubmit.isSelected){
                (activity as PlanActivity).replaceFragment(AddPlaceFragment(), "AddPlaceFragment")
            }else {
                // 선택되지 않았다면 알림을 주는 로직
                Toast.makeText(requireContext(), "미선택된 항목이 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateTimes(): List<String> {
        val times = mutableListOf<String>()
        for (hour in 0..23) {
            times.add(String.format("%02d:00", hour)) // 00분
            times.add(String.format("%02d:30", hour)) // 30분
        }
        return times
    }

    private fun updateArrivalRecyclerView(departHour: Int, departMinute: Int) {
        // 도착 시간은 출발 시간 이후로만 가능하게 필터링
        val filteredTimes = generateTimes()

        val arrivalAdapter = TimeAdapter(filteredTimes, { selectedTime ->
            // 도착 시간 선택 처리
            //binding.rvArrival.text = "도착 시간: $selectedTime"
        }, { time ->
            // 출발 시간 이전 시간은 회색으로 표시
            val hour = time.split(":")[0].toInt()
            val minute = time.split(":")[1].toInt()
            hour < departHour || (hour == departHour && minute <= departMinute) // 출발 시간 이전인지 확인
        })

        binding.rvArrival.adapter = arrivalAdapter
    }

    private fun setListener(svSearch:SearchView) {
        // SearchView에서 생성되는 타자에 있는 돋보기 버튼 활성화
        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(svSearch==binding.svSearchDepart) timeViewModel.getDepart(true)
                else timeViewModel.getArrival(true)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }

    // 두 값이 모두 true인지 확인하는 함수
    private fun checkNextButtonState() {
        val isDepartDecided = timeViewModel.decideDepart.value ?: false
        val isArrivalDecided = timeViewModel.decideArrival.value ?: false
        binding.btnSubmit.isSelected = isDepartDecided && isArrivalDecided
    }


    /*private fun setting() {
        setListener(binding.svSearchDepart)
        setListener(binding.svSearchArrival)

        // ViewModel 데이터 관찰
        timeViewModel.decideDepart.observe(viewLifecycleOwner) { checkNextButtonState() }
        timeViewModel.decideArrival.observe(viewLifecycleOwner) { checkNextButtonState() }

        // 현재 시간으로 TimePicker 초기화
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // TimePicker 초기화
        binding.tpDepart.hour = currentHour
        binding.tpDepart.minute = currentMinute

        // 출발 시간을 설정할 때 TimePickerDialog로 처리
        binding.tpDepart.setOnClickListener {
            showTimePickerDialog(currentHour, currentMinute) { selectedHour, selectedMinute ->
                binding.tpDepart.hour = selectedHour
                binding.tpDepart.minute = selectedMinute

                // 출발 시간 설정 후 도착 시간 TimePickerDialog 열기
                binding.tpArrival.setOnClickListener {
                    showArrivalTimePickerDialog(selectedHour, selectedMinute)
                }
            }
        }

        // 24시간 형식으로 설정
        binding.tpDepart.setIs24HourView(true)
        binding.tpArrival.setIs24HourView(true)
    }



    private fun showTimePickerDialog(hour: Int, minute: Int, onTimeSelected: (Int, Int) -> Unit) {
        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            onTimeSelected(selectedHour, selectedMinute)
        }, hour, minute, true).show()
    }

    private fun showArrivalTimePickerDialog(departHour: Int, departMinute: Int) {
        val calendar = Calendar.getInstance()

        // 출발 시간 이후만 보여주는 TimePickerDialog 설정
        val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            binding.tpArrival.hour = hourOfDay
            binding.tpArrival.minute = minute
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

        // 출발 시간 이전은 선택할 수 없도록 설정 (출발 시간 이후만 보여줌)
        timePickerDialog.updateTime(departHour, departMinute + 1)
        timePickerDialog.show()
    }



    private fun updateArrivalTimePicker(departHour: Int, departMinute: Int) {
        // tp_arrival의 시간을 tp_depart 이후로 설정
        binding.tpArrival.setIs24HourView(true) // 24시간 형식 유지

        // tp_arrival의 최소 시간을 출발 시간 이후로 설정
        binding.tpArrival.setOnTimeChangedListener { _, hourOfDay, minute ->
            // 출발 시간 이전의 시간은 선택 불가하게 함
            if (hourOfDay < departHour || (hourOfDay == departHour && minute <= departMinute)) {
                binding.tpArrival.hour = departHour
                binding.tpArrival.minute = departMinute + 1
            }
        }

        // 출발 시간 이후로 도착 시간을 설정할 수 있도록 최소 시간 제한
        if (binding.tpArrival.hour < departHour || (binding.tpArrival.hour == departHour && binding.tpArrival.minute <= departMinute)) {
            binding.tpArrival.hour = departHour
            binding.tpArrival.minute = departMinute + 1 // 최소 1분 이후로 설정
        }
    }*/
}