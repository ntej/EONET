package com.example.eonet.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eonet.domain.utils.divertErrors
import com.example.eonet.data.repos.EOCategoriesRepo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HostViewModel @Inject constructor() :ViewModel() {

//    val error = PublishSubject.create<Throwable>()
//
//    val eoCategories = eoCategoriesRepo.getEOCategories()
//        .doOnError {
//            Log.i(javaClass.simpleName, "Error came to VM...diverting")
//        }
//        .divertErrors(error)

}