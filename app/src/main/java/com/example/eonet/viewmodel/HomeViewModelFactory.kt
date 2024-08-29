package com.example.eonet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eonet.repos.EOCategoriesRepo
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(val eoCategoriesRepo: EOCategoriesRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(eoCategoriesRepo) as T
    }
}