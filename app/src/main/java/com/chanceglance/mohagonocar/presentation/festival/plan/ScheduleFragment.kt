package com.chanceglance.mohagonocar.presentation.festival.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.databinding.FragmentScheduleBinding
import java.util.Calendar

class ScheduleFragment:Fragment() {
    private var _binding: FragmentScheduleBinding?= null
    private val binding: FragmentScheduleBinding
        get()= requireNotNull(_binding) {"null"}

    private val planViewModel:PlanViewModel by activityViewModels()

    private var currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    private var currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private lateinit var selectedDay:String
    private val festivalMonth = Calendar.OCTOBER  // 축제의 달
    private val festivalYear = 2024  // 축제의 연도

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentScheduleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting(){
        // 초기 달력 설정
        updateCalendarView()

        // Prev 버튼 클릭
        binding.btnPrev.setOnClickListener {
            currentMonth--
            if (currentMonth < 0) {
                currentMonth = 11
                currentYear--
            }
            updateCalendarView()
        }

        // Next 버튼 클릭
        binding.btnNext.setOnClickListener {
            currentMonth++
            if (currentMonth > 11) {
                currentMonth = 0
                currentYear++
            }
            updateCalendarView()
        }

        binding.btnSubmit.setOnClickListener{
            if(binding.btnSubmit.isSelected){
                planViewModel.getDate(currentYear, currentMonth, selectedDay)
                if (binding.btnSubmit.isSelected) {
                    (activity as PlanActivity).replaceFragment(PlaceFragment(), "PlaceFragment")
                }
            } else {
                // 선택되지 않았다면 알림을 주는 로직
                Toast.makeText(requireContext(), "날짜를 선택하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCalendarView() {
        val tvYearMonth = binding.tvYearMonth
        val btnPrev = binding.btnPrev
        val btnNext = binding.btnNext
        val recyclerView = binding.rvCalendar

        // 년/월 업데이트
        tvYearMonth.text = "${currentYear}년 ${currentMonth + 1}월"

        // 축제 달에서는 Prev 버튼 숨기기
        if (currentYear == festivalYear && currentMonth == festivalMonth) {
            btnPrev.visibility = View.INVISIBLE
        } else {
            btnPrev.visibility = View.VISIBLE
        }

        // 다음 달을 눌렀을 때 Next 버튼을 숨기기 (원하는 조건에 따라 조정)
        if (currentMonth == (festivalMonth + 1) % 12 && currentYear == festivalYear) {
            btnNext.visibility = View.INVISIBLE
        } else {
            btnNext.visibility = View.VISIBLE
        }

        // 축제 달이면 축제 날짜를 적용
        val festivalDates = if (currentYear == festivalYear && currentMonth == festivalMonth) {
            listOf("17", "18", "19", "20") // 축제 날짜 리스트
        } else {
            emptyList() // 축제 달이 아닐 경우 빈 리스트
        }

        // 달력 업데이트 (현재 달의 날짜 데이터 생성)
        val days = generateDaysForMonth(currentYear, currentMonth)
        val adapter = CalendarAdapter(days, festivalDates) { selectedDay ->
            //Toast.makeText(this, "$selectedDay 선택됨", Toast.LENGTH_SHORT).show()
            planViewModel.getDate(currentYear, currentMonth, selectedDay)
            if(festivalDates.contains(selectedDay)){
                this.selectedDay=selectedDay
                binding.btnSubmit.text = getString(R.string.plan_submit, currentYear, currentMonth, selectedDay)
                binding.btnSubmit.setTextColor(getColor(requireContext(),R.color.white))
                binding.btnSubmit.isSelected=true
            }
            else {
                binding.btnSubmit.text = getString(R.string.plan_error)
                binding.btnSubmit.setTextColor(getColor(requireContext(),R.color.plan_error_red))
                binding.btnSubmit.isSelected=false
            }
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        recyclerView.adapter = adapter
    }


    fun generateDaysForMonth(year: Int, month: Int): List<String> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val days = mutableListOf<String>()

        // 첫 주의 빈칸 추가 (해당 월 시작 요일에 맞춰서 빈 칸 삽입)
        for (i in 1 until dayOfWeek) {
            days.add("")
        }

        // 해당 월의 날짜 추가
        for (i in 1..daysInMonth) {
            days.add(i.toString())
        }

        return days
    }

}