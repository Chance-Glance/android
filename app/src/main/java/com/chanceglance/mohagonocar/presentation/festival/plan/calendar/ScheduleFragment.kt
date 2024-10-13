package com.chanceglance.mohagonocar.presentation.festival.plan.calendar

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
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.databinding.FragmentScheduleBinding
import com.chanceglance.mohagonocar.presentation.festival.plan.PlaceFragment
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanActivity
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.Calendar

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding: FragmentScheduleBinding
        get() = requireNotNull(_binding) { "null" }

    private val planViewModel: PlanViewModel by activityViewModels()

    private var currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    private var currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private lateinit var selectedDay: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting() {
        val itemJsonString = arguments?.getString("festivalItem")
        val festivalItem = itemJsonString?.let { Json.decodeFromString<ResponseFestivalDto.Data.Item>(it) }!!

        // 초기 달력 설정
        updateCalendarView(festivalItem.activePeriod)

        // Prev 버튼 클릭
        binding.btnPrev.setOnClickListener {
            currentMonth--
            if (currentMonth < 0) {
                currentMonth = 11
                currentYear--
            }
            updateCalendarView(festivalItem.activePeriod)
            binding.btnPrev.visibility=View.INVISIBLE
            binding.btnNext.visibility=View.VISIBLE
        }

        // Next 버튼 클릭
        binding.btnNext.setOnClickListener {
            currentMonth++
            if (currentMonth > 11) {
                currentMonth = 0
                currentYear++
            }
            updateCalendarView(festivalItem.activePeriod)
            binding.btnPrev.visibility=View.VISIBLE
            binding.btnNext.visibility=View.INVISIBLE
        }

        binding.btnSubmit.setOnClickListener {
            if (binding.btnSubmit.isSelected) {
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

    private fun updateCalendarView(activePeriod: ResponseFestivalDto.Data.Item.ActivePeriod) {
        binding.tvFestivalSchedule.text=getString(R.string.festival_duration,activePeriod.startDate,activePeriod.endDate)
        val tvYearMonth = binding.tvYearMonth
        val recyclerView = binding.rvCalendar

        val festivalStartMonth = activePeriod.startDate.monthValue - 1 // 0-based month
        val festivalEndMonth = activePeriod.endDate.monthValue - 1

        // 년/월 업데이트
        tvYearMonth.text = "${currentYear}년 ${currentMonth + 1}월"

        // 축제 달이면 축제 날짜를 적용
        val startDay = if (currentMonth == festivalStartMonth) activePeriod.startDate.dayOfMonth else 1
        val endDay = if (currentMonth == festivalEndMonth) activePeriod.endDate.dayOfMonth else {
            generateDaysForMonth(currentYear, currentMonth).size
        }

        val festivalDates = (startDay..endDay).map { it.toString() }

        // 달력 업데이트 (현재 달의 날짜 데이터 생성)
        val days = generateDaysForMonth(currentYear, currentMonth)
        val adapter = CalendarAdapter(days, festivalDates) { selectedDay ->
            planViewModel.getDate(currentYear, currentMonth, selectedDay)
            if (festivalDates.contains(selectedDay)) {
                this.selectedDay = selectedDay
                with(binding){
                    btnSubmit.text =
                        getString(R.string.plan_submit, currentYear, currentMonth+1, selectedDay)
                    btnSubmit.setTextColor(getColor(requireContext(), R.color.white))
                    btnSubmit.isSelected = true
                }
            } else {
                with(binding){
                    btnSubmit.text = getString(R.string.plan_error)
                    btnSubmit.setTextColor(getColor(requireContext(), R.color.plan_error_red))
                    btnSubmit.isSelected = false
                }
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