package com.chanceglance.mohagonocar.presentation.festival.plan.course

sealed class CourseItem {
    data class Place(val name: String) : CourseItem()
    data class SubPath(
        val pathType: String,
        val startPlaceName: String,
        val endPlaceName: String,
        val sectionTime: Int
    ) : CourseItem()
    data class EndPlace(val name: String) : CourseItem() // 마지막 장소를 위한 모델
}