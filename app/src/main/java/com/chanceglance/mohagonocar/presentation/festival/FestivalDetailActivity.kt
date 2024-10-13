package com.chanceglance.mohagonocar.presentation.festival

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.databinding.ActivityFestivalDetailBinding
import com.chanceglance.mohagonocar.presentation.festival.plan.PlanActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FestivalDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFestivalDetailBinding
    private lateinit var festivalDotAdapter: FestivalDotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()

        setting()
    }

    private fun initBinds() {
        binding = ActivityFestivalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        val itemJsonString = intent.getStringExtra("festivalItem")
        val item = itemJsonString?.let { Json.decodeFromString<ResponseFestivalDto.Data.Item>(it) }

        festivalDotAdapter = FestivalDotAdapter(this)
        festivalDotAdapter.getImageList(item!!.imageUrlList)

        binding.vpImages.adapter = festivalDotAdapter
        binding.tlDots.setupWithViewPager(binding.vpImages, true)

        clickBackButton()
        clickPlanButton()

        val fragment = FestivalDetailFragment().apply {
            arguments = Bundle().apply {
                putString("festivalItem", itemJsonString) // item을 JSON 형태로 전달
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_festival_detail, fragment)
//            .addToBackStack(null)
            .commit()
    }

    private fun clickBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun clickPlanButton() {
        binding.btnPlan.setOnClickListener {
            val intent = Intent(this, PlanActivity::class.java)
            startActivity(intent)
        }
    }
}