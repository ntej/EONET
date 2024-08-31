package com.example.eonet.repos

import com.example.eonet.model.CategoryData
import com.example.eonet.retrofit.IEonetAPI
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EOCategoryDataRepo @Inject constructor(val eonetApi: IEonetAPI) {
    fun getCategoryData(categoryId: String):Observable<CategoryData>{
        return  eonetApi.getCategory(categoryId)
    }
}