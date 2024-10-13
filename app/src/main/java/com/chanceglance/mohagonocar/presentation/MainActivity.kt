package com.chanceglance.mohagonocar.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.databinding.ActivityMainBinding
import com.chanceglance.mohagonocar.presentation.festival.FestivalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds(){
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting(){
        replaceFragment(FestivalFragment())
        with(binding){
            btnFestival.isSelected=true
            btnTravel.isSelected=false
            btnFestival.setTextColor(Color.WHITE)

            btnFestival.setOnClickListener{
                clickFestivalButton()
            }
            btnTravel.setOnClickListener{
                clickTravelButton()
            }
        }
    }

    private fun clickFestivalButton(){
        with(binding){
            btnFestival.isSelected=true
            btnTravel.isSelected=false
            btnFestival.setTextColor(Color.WHITE)
            btnTravel.setTextColor(Color.BLACK)
        }

        replaceFragment(FestivalFragment())
    }

    private fun clickTravelButton(){
        with(binding){
            btnFestival.isSelected=false
            btnTravel.isSelected=true
            btnFestival.setTextColor(Color.BLACK)
            btnTravel.setTextColor(Color.WHITE)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
//            .addToBackStack(null)
            .commit()
    }
}