package com.example.eonet.domain

import com.example.eonet.domain.error.ApiResult
import com.example.eonet.data.repos.EOCategoryDataRepo
import com.example.eonet.entities.Event
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CategoriesDataInteractor @Inject constructor(private val eoCategoryDataRepo: EOCategoryDataRepo) {
    fun getCategories(daysSelected : BehaviorSubject<Int>, categoryId :String):Observable<List<Event>>{

      return  Observables.combineLatest(
            daysSelected,
            eoCategoryDataRepo.getCategoryData(categoryId).toObservable()
        ){ daysSelected, apiResult ->
            // Ensure apiResult is a Success
            val events = when (apiResult) {
                is ApiResult.Success -> apiResult.data
                is ApiResult.Error -> {
                    return@combineLatest emptyList<Event>()
                }
            }
            // Current date
            val currentDate = LocalDateTime.now()
            // Calculate the start date by subtracting sliderPosition daysSelected from the current date
            val startDate = currentDate.minusDays(daysSelected.toLong())
            // Parse date format
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

            events.filter { event ->
                val eventDate = LocalDateTime.parse(event.geometry.get(0).date, formatter)
                eventDate.isAfter(startDate) && eventDate.isBefore(currentDate)
            }
        }

    }
}