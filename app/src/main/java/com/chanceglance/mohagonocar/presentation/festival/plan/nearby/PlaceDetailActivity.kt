package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.databinding.ActivityPlaceDetailBinding
import com.chanceglance.mohagonocar.presentation.festival.FestivalDetailFragment
import com.chanceglance.mohagonocar.presentation.festival.FestivalDotAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PlaceDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceDetailBinding
    private lateinit var imagesAdapter: FestivalDotAdapter
    private var isOn = false
    private val placeDetailViewModel:PlaceDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()

        setting()
    }

    private fun initBinds() {
        binding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        isOn=intent.getBooleanExtra("isSelect", false)
        if(isOn) changeButton()

        val itemJsonString = intent.getStringExtra("placeItem")
        val item =
            itemJsonString?.let { Json.decodeFromString<ResponseNearbyPlaceDto.Data.Item>(it) }

        placeDetailViewModel.setPlace(item!!)

        imagesAdapter = FestivalDotAdapter(this)

        with(binding) {
            tvName.text = item!!.name
            tvLocation.text = item.address
            tvDistinguish.text = item.placeType

            imagesAdapter.getImageList(item.imageUrlList)
            vpImages.adapter = imagesAdapter
            tlDots.setupWithViewPager(binding.vpImages, true)

            btnSelect.setOnClickListener {
                toggleButton()
            }
            btnBack.setOnClickListener {
                finish()
            }
        }

        val placeFragment = PlaceDetailFragment().apply {
            arguments = Bundle().apply {
                putString("placeItem", itemJsonString) // item을 JSON 형태로 전달
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_place, placeFragment)
//            .addToBackStack(null)
            .commit()
    }

    private fun changeButton(){
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.btnSelect) // btnSelect는 ConstraintLayout이어야 함
        constraintSet.setHorizontalBias(R.id.iv_slider, 0.8f)

        // 변경된 제약을 btnSelect에 적용
        constraintSet.applyTo(binding.btnSelect)

        binding.btnSelect.setBackgroundResource(R.drawable.btn_background_selected)
        binding.ivSlider.background = ColorDrawable(ContextCompat.getColor(this, R.color.white))
    }

    private fun toggleButton() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.btnSelect) // btnSelect는 ConstraintLayout이어야 함

        // isOn에 따라 슬라이더 위치 변경
        val endBias = if (isOn) 0.2f else 0.8f
        constraintSet.setHorizontalBias(R.id.iv_slider, endBias)

        // 애니메이션 적용
        val transition = ChangeBounds()
        transition.duration = 300
        TransitionManager.beginDelayedTransition(binding.btnSelect, transition)
        constraintSet.applyTo(binding.btnSelect)

        // 색상 애니메이션 적용
        val startColor = if (isOn) getColor(R.color.white) else getColor(R.color.plan_btn_purple)
        val endColor = if (isOn) getColor(R.color.plan_btn_purple) else getColor(R.color.white)

        ObjectAnimator.ofObject(binding.ivSlider, "backgroundColor", ArgbEvaluator(), startColor, endColor)
            .apply {
                duration = 300
                start()
            }

        // 배경 변경
        val backgroundDrawable =
            if (isOn) R.drawable.btn_background else R.drawable.btn_background_selected
        binding.btnSelect.setBackgroundResource(backgroundDrawable)

        isOn = !isOn
    }

    override fun onBackPressed() {
        val place: ResponseNearbyPlaceDto.Data.Item = placeDetailViewModel.getPlace()
        val itemJsonString = Json.encodeToString(
            ResponseNearbyPlaceDto.Data.Item.serializer(),
            place
        )

        val intent = Intent()
        intent.putExtra("selectedItem",itemJsonString) // 선택된 아이템의 ID 전달
        intent.putExtra("isItemSelected", isOn) // 버튼 선택 상태 전달
        setResult(RESULT_OK, intent)

        Log.d("PlaceDetailActivity", "Item sent: ${place.name}") // 디버그용 로그 추가
        super.onBackPressed()
    }

}