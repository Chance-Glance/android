package com.chanceglance.mohagonocar.presentation.festival

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.chanceglance.mohagonocar.databinding.ActivityFestivalDetailBinding

class FestivalDetailActivity:AppCompatActivity() {
    private lateinit var binding:ActivityFestivalDetailBinding
    private lateinit var festivalDotAdapter: FestivalDotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()

        setting()
    }

    private fun initBinds(){
        binding=ActivityFestivalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting(){
        festivalDotAdapter = FestivalDotAdapter(this)

        binding.vpImages.adapter = festivalDotAdapter
        binding.tlDots.setupWithViewPager(binding.vpImages, true)

        clickBackButton()
    }

    private fun clickBackButton(){
        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}