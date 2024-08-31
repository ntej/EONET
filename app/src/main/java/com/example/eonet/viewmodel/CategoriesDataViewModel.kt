package com.example.eonet.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eonet.model.Event
import com.example.eonet.repos.EOCategoryDataRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CategoriesDataViewModel @Inject constructor(private val eoCategoryDataRepo: EOCategoryDataRepo) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    val daysSelected = BehaviorSubject.createDefault(360)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCategoryEvents(categoryId: String) {
        Observables.combineLatest(
            daysSelected,
            eoCategoryDataRepo.getCategoryData(categoryId)
        ) { daysSelected, categoryData ->
            // Current date
            val currentDate = LocalDateTime.now()
            // Calculate the start date by subtracting sliderPosition daysSelected from the current date
            val startDate = currentDate.minusDays(daysSelected.toLong())
            // Parse date format
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            categoryData.events.filter { event ->
                val eventDate = LocalDateTime.parse(event.geometry.get(0).date, formatter)
                eventDate.isAfter(startDate) && eventDate.isBefore(currentDate)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _events.value = it
            }
            .addTo(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}