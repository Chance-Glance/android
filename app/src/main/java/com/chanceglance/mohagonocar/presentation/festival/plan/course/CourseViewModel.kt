package com.chanceglance.mohagonocar.presentation.festival.plan.course

import androidx.lifecycle.ViewModel
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import kotlinx.coroutines.currentCoroutineContext

class CourseViewModel : ViewModel() {

    fun replaceList(data: ResponseTravelCourseDto): List<CourseItem> {
        val list: MutableList<CourseItem> = mutableListOf()
        var end = ""
        for (course in data.data) {
            val start = course.startPlaceName
            list.addAll(listOf(CourseItem.Place(name = start)))
            for (path in course.subPaths) {
                when (path) {
                    is ResponseTravelCourseDto.Data.SubPath.Walk -> {
                        // `Walk` 타입일 경우
                        val type = path.pathType
                        val startPlace = course.startPlaceName
                        val endPlace = course.endPlaceName
                        val time = path.sectionTime

                        list.add(
                            CourseItem.SubPath(
                                pathType = type,
                                startPlaceName = startPlace,
                                endPlaceName = endPlace,
                                sectionTime = time
                            )
                        )
                    }

                    is ResponseTravelCourseDto.Data.SubPath.Bus -> {
                        // `Bus` 타입일 경우
                        val type = path.pathType
                        val startPlace = path.startPlaceName ?: course.startPlaceName
                        val endPlace = path.endPlaceName ?: course.endPlaceName
                        val time = path.sectionTime

                        list.add(
                            CourseItem.SubPath(
                                pathType = type,
                                startPlaceName = startPlace,
                                endPlaceName = endPlace,
                                sectionTime = time
                            )
                        )
                    }

                    is ResponseTravelCourseDto.Data.SubPath.Subway -> {
                        // `Subway` 타입일 경우
                        val type = path.pathType
                        val startPlace = path.startPlaceName
                        val endPlace = path.endPlaceName
                        val time = path.sectionTime

                        list.add(
                            CourseItem.SubPath(
                                pathType = type,
                                startPlaceName = startPlace,
                                endPlaceName = endPlace,
                                sectionTime = time
                            )
                        )
                    }
                }
                end = course.endPlaceName
            }
        }
        list.addAll(listOf(CourseItem.EndPlace(name = end)))
        return list
    }
}