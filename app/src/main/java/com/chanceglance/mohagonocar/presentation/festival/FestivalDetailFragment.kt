package com.chanceglance.mohagonocar.presentation.festival

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chanceglance.mohagonocar.R
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.databinding.FragmentFestivalDetailBinding
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FestivalDetailFragment:Fragment() {
    private var _binding: FragmentFestivalDetailBinding?= null
    private val binding: FragmentFestivalDetailBinding
        get()= requireNotNull(_binding) {"null"}

    private lateinit var festivalItem:ResponseFestivalDto.Data.Item
    private lateinit var kakaoMapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFestivalDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
    }

    private fun setting(){
        // 전달된 데이터를 수신
        val itemJsonString = arguments?.getString("festivalItem")
        festivalItem = itemJsonString?.let { Json.decodeFromString<ResponseFestivalDto.Data.Item>(it) }!!

        Log.d("festivaldetailfragment","festivalDetailFragment - ${festivalItem.name}")
        // 수신한 데이터 사용
        festivalItem?.let {item->
            with(binding){
                tvName.text=item.name
                tvDate.text=getString(R.string.festival_duration, item.activePeriod.startDate, item.activePeriod.endDate)
                tvLocation.text=item.address

                val description = item.description
                val modifiedDescription = if (description.endsWith("더보기")) {
                    description.substring(0, description.length - "더보기".length) // "더보기"를 제외한 부분만 가져옴
                } else {
                    description // "더보기"가 없으면 전체 텍스트 그대로 사용
                }
                tvDescription.text = modifiedDescription

            }
        }

        kakaoMapView = binding.mapView
        setKakaoMap(festivalItem.location)
    }

    private fun setKakaoMap(location: ResponseFestivalDto.Data.Item.Location) {
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
    }
}