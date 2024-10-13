package com.chanceglance.mohagonocar.presentation.festival.plan.time

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanceglance.mohagonocar.databinding.ItemTimeBinding
import com.chanceglance.mohagonocar.R

class TimeAdapter(
    private val times: List<String>,
    private val onTimeSelected: (String) -> Unit,
    private val isDisabled: (String) -> Boolean // 글자색을 바꾸기 위한 조건 함수 추가
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private var selectedPosition: Int? = null // 선택된 시간의 위치를 저장

    inner class TimeViewHolder(private val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root) {

        // onBind 함수로 아이템에 대한 처리를 간결하게 관리
        fun onBind(time: String, isDisabled: Boolean, position: Int) {
            binding.btnTime.text = time

            // 선택된 시간은 강조 표시
            if (position == selectedPosition) {
                binding.btnTime.isSelected=true
            } else {
                binding.btnTime.isSelected=false
            }

            // 출발 시간 이전이면 회색 처리 및 클릭 비활성화
            if (isDisabled) {
                binding.btnTime.setTextColor(itemView.context.getColor(R.color.time_no_grey))
                binding.btnTime.isEnabled = false
            } else {
                binding.btnTime.setTextColor(itemView.context.getColor(android.R.color.black))
                binding.btnTime.isEnabled = true
            }

            // 버튼 클릭 이벤트 처리
            binding.btnTime.setOnClickListener {
                if (!isDisabled) {
                    selectedPosition = position // 선택된 위치 저장
                    onTimeSelected(time)
                    notifyDataSetChanged() // 선택 상태를 업데이트
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding = ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val time = times[position]
        holder.onBind(time, isDisabled(time), position)
    }

    override fun getItemCount(): Int {
        return times.size
    }
}
