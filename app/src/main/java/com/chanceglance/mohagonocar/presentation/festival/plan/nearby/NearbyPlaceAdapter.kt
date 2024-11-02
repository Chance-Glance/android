package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.databinding.ItemPlaceBinding

class NearbyPlaceAdapter(private val context:Context,  private val clickButton: (ResponseNearbyPlaceDto.Data.Item) -> Unit, private val onItemClicked: (ResponseNearbyPlaceDto.Data.Item) -> Unit): RecyclerView.Adapter<NearbyPlaceAdapter.AddPlaceViewHolder>(){

    private val placeList:MutableList<ResponseNearbyPlaceDto.Data.Item> = mutableListOf()
    private val selectPlaceList:MutableList<ResponseNearbyPlaceDto.Data.Item> = mutableListOf()

    inner class AddPlaceViewHolder(
        private val binding:ItemPlaceBinding
    ):RecyclerView.ViewHolder(binding.root){
        private var isOn = false

        fun onBind(item: ResponseNearbyPlaceDto.Data.Item){
            Log.d("ImageList", "imageUrlList size: ${item.imageUrlList.size}")
            Log.d("ImageList", "First image URL: ${item.imageUrlList.getOrNull(0)}")

            with(binding){
                tvName.text=item.name
                tvLocation.text=item.address
                ivImage.load(item.imageUrlList.getOrNull(0) ?: R.drawable.cat) {
                    transformations(RoundedCornersTransformation(30f)) // 30f로 모서리 둥글게
                    error(R.drawable.cat) // 로드 실패 시 기본 이미지
                }

                // 선택 상태를 selectPlaceList에 따라 설정
                isOn = selectPlaceList.contains(item)
                updateButtonAppearance(isOn)

                root.setOnClickListener{
                    onItemClicked(item)
                }
                btnSelect.setOnClickListener{
                    toggleButton(item)
                    clickButton(item)
                }
            }
        }

        // 버튼 상태 업데이트 함수
        private fun updateButtonAppearance(isSelected: Boolean) {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.btnSelect) // btnSelect는 ConstraintLayout이어야 함

            // isSelected에 따라 슬라이더 위치 변경
            val endBias = if (isSelected) 0.8f else 0.2f
            constraintSet.setHorizontalBias(R.id.iv_slider, endBias)

            // 애니메이션 적용
            val transition = ChangeBounds()
            transition.duration = 300
            TransitionManager.beginDelayedTransition(binding.btnSelect, transition)
            constraintSet.applyTo(binding.btnSelect)

            // 색상 애니메이션 적용
            val startColor = if (isSelected) context.getColor(R.color.plan_btn_blue) else context.getColor(R.color.white)
            val endColor = if (isSelected) context.getColor(R.color.white) else context.getColor(R.color.plan_btn_blue)

            ObjectAnimator.ofObject(binding.ivSlider, "backgroundColor", ArgbEvaluator(), startColor, endColor)
                .apply {
                    duration = 300
                    start()
                }

            // 배경 변경
            val backgroundDrawable =
                if (isSelected) R.drawable.btn_background_selected else R.drawable.btn_background
            binding.btnSelect.setBackgroundResource(backgroundDrawable)
        }

        private fun toggleButton(item: ResponseNearbyPlaceDto.Data.Item) {
            isOn = !isOn
            updateButtonAppearance(isOn)

            if(isOn) selectPlaceList.add(item)
            else selectPlaceList.remove(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPlaceViewHolder {
        val binding= ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return AddPlaceViewHolder(binding)
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: AddPlaceViewHolder, position: Int) {
        val item=placeList[position]
        holder.onBind(item)
    }

    fun getList(list:List<ResponseNearbyPlaceDto.Data.Item>){
        placeList.clear()
        placeList.addAll(list)
        notifyDataSetChanged()
    }

    // 선택된 상태 업데이트
    fun updateSelection(item: ResponseNearbyPlaceDto.Data.Item, isSelected: Boolean) {
        val index = placeList.indexOf(item)

        if(isSelected){
            selectPlaceList.add(item)
        } else{
            if(index != -1)
                selectPlaceList.remove(item)
        }

        if(index != -1)
            notifyItemChanged(index)
    }
}