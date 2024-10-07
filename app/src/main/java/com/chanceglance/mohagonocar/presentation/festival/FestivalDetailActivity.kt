package com.chanceglance.mohagonocar.presentation.festival

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.chanceglance.mohagonocar.databinding.ActivityFestivalDetailBinding
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanActivity

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
        clickPlanButton()
    }

    private fun clickBackButton(){
        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun clickPlanButton(){
        binding.btnPlan.setOnClickListener{
            val intent = Intent(this, PlanActivity::class.java)
            startActivity(intent)
        }
    }
}