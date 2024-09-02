package com.example.eonet.data.repos

import com.example.eonet.domain.error.ApiResult
import com.example.eonet.domain.error.ErrorHandler
import com.example.eonet.data.retrofit.IEonetAPI
import com.example.eonet.entities.Event
import io.reactivex.Single
import javax.inject.Inject

class EOCategoryDataRepo @Inject constructor(private val eonetApi: IEonetAPI) {
    fun getCategoryData(categoryId: String): Single<ApiResult<List<Event>>> {
        return eonetApi.getCategory(categoryId)
            .map { ApiResult.Success(it.events) as ApiResult<List<Event>> }
            .onErrorReturn {error->
                ApiResult.Error(ErrorHandler.handleError(error))
            }
    }
}