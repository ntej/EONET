package com.example.eonet.repos

import android.util.Log
import com.example.eonet.model.Categories
import com.example.eonet.retrofit.IEonetAPI
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class EOCategoriesRepo @Inject constructor(val eonetApi: IEonetAPI) {
    fun getEOCategories():Observable<Categories>{
        return eonetApi
            .getCategories()
            .doOnError { Log.e(javaClass.simpleName, "getEOCategories: Error in Repo",it ) }
    }
}