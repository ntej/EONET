package com.example.eonet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eonet.R
import com.example.eonet.retrofit.model.Category
import com.example.eonet.repos.EOCategoriesRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(eoCategoriesRepo: EOCategoriesRepo) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories
    init {
        eoCategoriesRepo.getEOCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _categories.value = it.categories
            }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}