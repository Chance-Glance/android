package com.chanceglance.mohagonocar.presentation.festival.plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chanceglance.mohagonocar.R

class CalendarAdapter(
    private val days: List<String>,
    private val festivalDates: List<String>,
    private val onDateClick: (String) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION // 선택된 버튼의 위치

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateButton: Button = itemView.findViewById(R.id.buttonDate)

        fun bind(day: String, isFestivalDate: Boolean, isSelected: Boolean, onDateClick: (String) -> Unit) {
            if (day.isEmpty()) {
                dateButton.visibility = View.INVISIBLE
            } else {
                dateButton.visibility = View.VISIBLE
                dateButton.text = day

                // 축제 날짜 처리
                if (isFestivalDate) {
                    dateButton.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.plan_btn_purple)
                    )
                    dateButton.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.white)
                    )
                } else {
                    dateButton.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.white)
                    )
                    dateButton.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.black)
                    )
                }

                // 선택된 버튼 처리
                if (isSelected) {
                    dateButton.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.black)
                    )
                    dateButton.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.white)
                    )
                }

                // 클릭 이벤트 처리
                dateButton.setOnClickListener {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition

                    // 이전에 선택된 버튼을 초기화하고 새로 선택된 버튼을 업데이트
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)

                    // 클릭된 날짜 전달
                    onDateClick(day)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_date, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day = days[position]
        val isFestivalDate = festivalDates.contains(day)
        val isSelected = position == selectedPosition
        holder.bind(day, isFestivalDate, isSelected, onDateClick)
    }

    override fun getItemCount(): Int = days.size
}
