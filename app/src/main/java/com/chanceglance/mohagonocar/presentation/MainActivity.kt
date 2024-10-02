package com.chanceglance.mohagonocar.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.databinding.ActivityMainBinding
import com.chanceglance.mohagonocar.presentation.festival.FestivalFragment

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
        with(binding){
            btnFestival.isSelected=true
            btnTravel.isSelected=false

            btnFestival.setOnClickListener{
                clickFestivalButton()
            }
            btnTravel.setOnClickListener{
                clickTravelButton()
            }
        }
    }

    private fun clickFestivalButton(){
        binding.btnFestival.isSelected=true
        binding.btnTravel.isSelected=false

        replaceFragment(FestivalFragment())
    }

    private fun clickTravelButton(){
        binding.btnFestival.isSelected=false
        binding.btnTravel.isSelected=true
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
//            .addToBackStack(null)
            .commit()

    }
}