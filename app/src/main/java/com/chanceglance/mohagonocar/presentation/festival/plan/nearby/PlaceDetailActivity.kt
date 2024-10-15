package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chanceglance.mohagonocar.databinding.ActivityPlaceDetailBinding
import com.chanceglance.mohagonocar.presentation.festival.FestivalDotAdapter

class PlaceDetailActivity:AppCompatActivity() {
    private lateinit var binding: ActivityPlaceDetailBinding
    private lateinit var festivalDotAdapter: FestivalDotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()

        setting()
    }

    private fun initBinds(){
        binding=ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting(){
        binding.btnBack.setOnClickListener{
            finish()
        }

        festivalDotAdapter = FestivalDotAdapter(this)

        binding.vpImages.adapter = festivalDotAdapter
        binding.tlDots.setupWithViewPager(binding.vpImages, true)
    }
}