package com.chanceglance.mohagonocar.presentation.festival.plan.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.databinding.FragmentCourseWithFestivalBinding
import com.kakao.vectormap.GestureType
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles

class CourseWithFestivalFragment:Fragment() {
    private var _binding: FragmentCourseWithFestivalBinding?= null
    private val binding: FragmentCourseWithFestivalBinding
        get()= requireNotNull(_binding) {"null"}

    private lateinit var kakaoMapView:MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCourseWithFestivalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting(){

        //kakaoMapView=binding.mvMap
        //setKakaoMap(ResponseFestivalDto.Data.Item.Location(37.406960, 127.115587))

    }

/*    private fun setKakaoMap(location: ResponseFestivalDto.Data.Item.Location) {
        kakaoMapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
                Log.d("placeDetailFragment", "Map destroyed")
            }

            override fun onMapError(error: Exception) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                Log.e("placeDetailFragment", "Map error: ${error.message}", error)
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                Log.d("placeDetailFragment", "mapready")

                // FestivalItem의 위치로 초기 위치 설정
                val festivalPosition = LatLng.from(location.latitude, location.longitude) // FestivalItem의 위치
                kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(festivalPosition))

                // label 생성
                val styles = kakaoMap.labelManager!!
                    .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.pin)))
                val options = LabelOptions.from(festivalPosition).setStyles(styles)
                val layer = kakaoMap.labelManager!!.layer
                layer?.addLabel(options)

                kakaoMap.setGestureEnable(GestureType.Zoom, true);
                kakaoMap.setGestureEnable(GestureType.OneFingerZoom, true);
            }

            override fun getPosition(): LatLng {
                // 위치 정보가 설정된 후 UI 업데이트를 메인 스레드에서 실행
                return LatLng.from(location.latitude, location.longitude)
            }

            override fun getZoomLevel(): Int {
                // 지도 시작 시 확대/축소 줌 레벨 설정
                return 15
            }

            override fun getViewName(): String {
                // KakaoMap 의 고유한 이름을 설정
                return "MohagoNoCarMap"
            }

            override fun isVisible(): Boolean {
                // 지도 시작 시 visible 여부를 설정
                return true
            }

            override fun getTag(): String {
                // KakaoMap 의 tag 을 설정
                return "MohagoNoCarTag"
            }
        })
    }*/
}