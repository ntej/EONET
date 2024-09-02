package com.example.eonet.view.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eonet.domain.error.ApiResult
import com.example.eonet.data.repos.EOCategoriesRepo
import com.example.eonet.entities.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(val eoCategoriesRepo: EOCategoriesRepo) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    /**
     * By keeping _errorState private, you prevent external code from modifying it.
     * External code can only read from errorState, ensuring that the ViewModel controls
     * when and how the error state is updated.
     */
    private val _errorState: MutableLiveData<String?> = MutableLiveData()
    val errorState: LiveData<String?> get() = _errorState

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _progressIndicatorLoading = MutableLiveData<Boolean>()
    val progressIndicatorLoading : LiveData<Boolean> get() = _progressIndicatorLoading


    private var categorySubscription: Disposable? = null // Track current subscription


    init {
        loadCategories()
    }

    private fun loadCategories() {
        _progressIndicatorLoading.value = true //Request to get Result, Loading is needed
        // Dispose of any previous subscription
        categorySubscription?.dispose()

        categorySubscription = eoCategoriesRepo.getEOCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _progressIndicatorLoading.value = false //Result is available, Loading is not needed
                when (result) {
                    is ApiResult.Success -> _categories.value = result.data
                    is ApiResult.Error -> _errorState.value = result.message
                }
            }

        categorySubscription?.addTo(compositeDisposable)
    }

    fun retry() {
        _errorState.value = null // Reset error state
        loadCategories()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}