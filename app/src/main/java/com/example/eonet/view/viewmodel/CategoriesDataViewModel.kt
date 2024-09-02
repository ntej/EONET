package com.example.eonet.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eonet.domain.CategoriesDataInteractor
import com.example.eonet.entities.Event
import com.example.eonet.data.repos.EOCategoryDataRepo
import com.example.eonet.domain.utils.DateFormatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CategoriesDataViewModel @Inject constructor(private val eoCategoryDataRepo: EOCategoryDataRepo,
                                                  private val dateFormatter: DateFormatter,
                                                  private val categoriesDataInteractor: CategoriesDataInteractor) :
    ViewModel() {

    private val _errorState: MutableLiveData<String?> = MutableLiveData()
    val errorState: LiveData<String?> get() = _errorState

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    private val _progressIndicatorLoading = MutableLiveData<Boolean>()
    val progressIndicatorLoading: LiveData<Boolean> get() = _progressIndicatorLoading

    val daysSelected = BehaviorSubject.createDefault(360)

    private var categoryDataSubscription: Disposable? = null // Track current subscription for retry

    private val compositeDisposable = CompositeDisposable()

    fun getCategoryEvents(categoryId: String) {
        _progressIndicatorLoading.value = true

        // Dispose of any previous subscription
        categoryDataSubscription?.dispose()

        categoryDataSubscription = categoriesDataInteractor.getCategories(daysSelected,categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {events->
                if(events.isNotEmpty()) {
                    _progressIndicatorLoading.value = false
                    _events.value = events
                }else{
                    _errorState.value = "Oops! There are no events available at the moment. Please check back later."
                }
            }
            .addTo(compositeDisposable)
    }

    fun retry(categoryId: String) {
        _errorState.value = null // Reset error state
        getCategoryEvents(categoryId)
    }

    fun formatDate(date:String):String {
        return dateFormatter.formatDate(date)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}