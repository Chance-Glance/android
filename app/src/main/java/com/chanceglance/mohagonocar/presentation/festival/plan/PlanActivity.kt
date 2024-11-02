package com.chanceglance.mohagonocar.presentation.festival.plan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.databinding.ActivityPlanBinding
import com.chanceglance.mohagonocar.presentation.festival.FestivalDetailFragment
import com.chanceglance.mohagonocar.presentation.festival.plan.calendar.ScheduleFragment
import com.chanceglance.mohagonocar.presentation.festival.plan.course.CourseWithFestivalFragment
import com.chanceglance.mohagonocar.presentation.festival.plan.nearby.NearbyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlanBinding
    private val planViewModel: PlanViewModel by viewModels()
    private val nearbyViewModel:NearbyViewModel by viewModels()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    companion object {
        const val REQUEST_CODE = 1001 // 원하는 고유 값으로 설정
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()

        setting()

    }

    private fun initBinds() {
        binding = ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting() {
        val itemJsonString = intent.getStringExtra("festival")
        val item = itemJsonString?.let { Json.decodeFromString<ResponseFestivalDto.Data.Item>(it) }

        binding.tvName.text = item!!.name
        binding.tvLocation.text = item!!.address

        val scheduleFragment = ScheduleFragment().apply {
            arguments = Bundle().apply {
                putString("festivalItem", itemJsonString) // item을 JSON 형태로 전달
            }
        }

        if (supportFragmentManager.findFragmentByTag("ScheduleFragment") == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_plan, scheduleFragment, "ScheduleFragment")
                .commit()
        }

        // 뒤로가기 버튼 설정
        binding.btnBack.setOnClickListener {
            handleBackPressed()
        }

        planViewModel.selectedDate.observe(this) { date ->
            // LocalDate의 year, month, dayOfMonth를 사용
            val year = date.year
            val month = date.monthValue // month는 1~12로 제공
            val day = date.dayOfMonth

            // SimpleDateFormat을 사용하여 날짜 포맷 설정 (e.g., "11 Aug")
            val formattedDate = formatDateToDayMonth(year, month-1, day)
            binding.btnStart.text = formattedDate
            binding.btnEnd.text = formattedDate
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPressed() // 기본 뒤로 가기 버튼 동작을 handleBackPressed로 대체
            }
        })


    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()

        // 현재 보이는 프래그먼트를 숨김
        /*val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_plan)
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }*/

        // 새 프래그먼트를 보여줌, 없으면 추가
        transaction.replace(R.id.fcv_plan, fragment)
        transaction.addToBackStack(null) // 백스택에 추가
        Log.d("planActivity", "replaceFragment - Fragment ${fragment.javaClass.simpleName} added to backstack")
        transaction.commit()
    }

    fun showCourseFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fcv_course, fragment) // replace로 변경
            .addToBackStack("fcv_course_fragment") // 태그를 추가하여 백스택에 넣음
            .commit()

        binding.fcvCourse.visibility = View.VISIBLE
        showCourseText()
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
        Log.d("PlanActivity", "Current count: ${fragmentManager.backStackEntryCount}")

        if (fragmentManager.backStackEntryCount > 0) {
            // 백스택의 최상위 프래그먼트를 확인
            val currentFragment = fragmentManager.findFragmentById(R.id.fcv_plan)
            Log.d("PlanActivity", "Current fragment: ${currentFragment?.javaClass?.simpleName}")

            if (currentFragment is CourseWithFestivalFragment) {
                // 최상위 프래그먼트가 CourseWithFestivalFragment인 경우 deleteFcvCourse 호출
                deleteFcvCourse()
            }

            // 이전 프래그먼트로 돌아감
            fragmentManager.popBackStack()
        } else {
            // 백스택이 비어 있으면 액티비티 종료
            finish()
        }
    }

    /*private fun handleBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            // 백스택에 프래그먼트가 있으면 이전 프래그먼트로 돌아감
            // 백스택의 최상위 프래그먼트를 확인
            val currentFragment = fragmentManager.findFragmentById(R.id.fcv_plan)

            if (currentFragment is CourseWithFestivalFragment) {
                // 최상위 프래그먼트가 CourseWithFestivalFragment인 경우 hideFcvCourse 호출
                deleteFcvCourse()
            }

            // 이전 프래그먼트로 돌아감
            fragmentManager.popBackStack()
        } else {
            // 백스택이 비어 있으면 액티비티 종료
            finish()
        }
    }*/


    private fun showCourseText() {
        // BottomSheetBehavior 설정
        bottomSheetBehavior = BottomSheetBehavior.from(binding.fcvCourse)

        // BottomSheet의 초기 상태를 Collapsed로 설정
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // BottomSheet의 peek height를 250dp로 설정 (최소화면 높이)
        bottomSheetBehavior.peekHeight = (250 * resources.displayMetrics.density).toInt()

        // BottomSheet의 상태 변화 이벤트 처리
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 상태 변화에 따른 처리
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        println("BottomSheet Expanded")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        println("BottomSheet Collapsed")
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // slideOffset을 사용하지 않음, 최대화면과 최소화면 상태만 사용
                // slideOffset은 자동으로 0~1 범위 내에서 상태를 전환
            }
        })

        /*bottomSheetBehavior = BottomSheetBehavior.from(binding.fcvCourse)

        // BottomSheet의 초기 상태를 Collapsed로 설정
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // BottomSheet의 peek height를 400dp로 설정
        bottomSheetBehavior.peekHeight = (250 * resources.displayMetrics.density).toInt()

        // BottomSheet의 상태 변화 이벤트 처리
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 상태 변화에 따른 처리
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        println("BottomSheet Expanded")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        println("BottomSheet Collapsed")
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // slideOffset은 -1(숨김)부터 1(전체화면)까지 범위를 가짐
                if (slideOffset > 0) { // 슬라이드가 중간 이상일 때만 반응
                    val maxHeight = binding.root.height // 부모 뷰의 전체 높이
                    val newHeight = (250 * resources.displayMetrics.density + slideOffset * (maxHeight - 400 * resources.displayMetrics.density)).toInt()

                    // BottomSheet의 높이를 슬라이드 위치에 따라 조정
                    bottomSheet.layoutParams.height = newHeight
                    bottomSheet.requestLayout()
                }
            }
        })*/
        /*bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 상태 변화에 따른 처리
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // 확장 상태
                        println("BottomSheet Expanded")
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // 축소 상태
                        println("BottomSheet Collapsed")
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        // 드래그 중
                    }

                    BottomSheetBehavior.STATE_SETTLING -> {
                        // 정착 중 (멈추기 직전)
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // 숨김 상태
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드 중일 때의 동작 처리 (예: 슬라이드된 퍼센트를 이용한 UI 변화)
            }
        })*/
    }

    // fcvCourse의 visibility를 제어하는 메서드 추가
    fun deleteFcvCourse() {
        supportFragmentManager.popBackStack("fcv_course_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        binding.fcvCourse.visibility = View.GONE
    }

    /*// Android의 기본 뒤로가기 동작 처리
    override fun onBackPressed() {
        super.onBackPressed()
        handleBackPressed() // 기본 동작을 이 메서드로 대체
    }*/
}