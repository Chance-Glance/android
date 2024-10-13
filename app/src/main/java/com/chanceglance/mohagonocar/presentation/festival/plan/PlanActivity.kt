package com.chanceglance.mohagonocar.presentation.festival.plan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.databinding.ActivityPlanBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PlanActivity:AppCompatActivity() {
    private lateinit var binding:ActivityPlanBinding
    private val planViewModel: PlanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()

        setting()

    }

    private fun initBinds(){
        binding= ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting(){
        if (supportFragmentManager.findFragmentByTag("ScheduleFragment") == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_plan, ScheduleFragment(), "ScheduleFragment")
                .commit()
        }

        // 뒤로가기 버튼 설정
        binding.btnBack.setOnClickListener {
            handleBackPressed()
        }

        // ViewModel 데이터 관찰
        planViewModel.selectedDate.observe(this) { date ->
            val (year, month, day) = date
            // SimpleDateFormat을 사용하여 날짜 포맷 설정 (e.g., "11 Aug")
            val formattedDate = formatDateToDayMonth(year, month, day)
            binding.btnStart.text = formattedDate
            binding.btnEnd.text = formattedDate
        }
    }

    fun replaceFragment(fragment: Fragment, tag: String){
        val transaction = supportFragmentManager.beginTransaction()

        // 현재 보이는 프래그먼트를 숨김
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_plan)
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        // 새 프래그먼트를 보여줌, 없으면 추가
        val fragmentInStack = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentInStack != null) {
            transaction.show(fragmentInStack)
        } else {
            transaction.add(R.id.fcv_plan, fragment, tag)
        }

        transaction.addToBackStack(null)
        transaction.commit()
    }

    // 날짜를 "dd MMM" 형식으로 변환하는 함수
    private fun formatDateToDayMonth(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }

        // "dd MMM" 형식으로 포맷, 예: "11 Aug"
        val dateFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)
        return dateFormat.format(calendar.time)
    }

    // 뒤로가기 버튼 눌렀을 때의 처리
    private fun handleBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            // 백스택에 프래그먼트가 있으면 이전 프래그먼트로 돌아감
            fragmentManager.popBackStack()
        } else {
            // 백스택이 비어 있으면 액티비티 종료
            finish()
        }
    }

    // Android의 기본 뒤로가기 동작 처리
    override fun onBackPressed() {
        super.onBackPressed()
        handleBackPressed() // 기본 동작을 이 메서드로 대체
    }
}