package com.example.eonet.data.repos

import com.example.eonet.domain.error.ApiResult
import com.example.eonet.domain.error.ErrorHandler.handleError
import com.example.eonet.data.retrofit.IEonetAPI
import com.example.eonet.entities.Category
import io.reactivex.Single
import javax.inject.Inject

class EOCategoriesRepo @Inject constructor(val eonetApi: IEonetAPI) {

    fun getEOCategories(): Single<ApiResult<List<Category>>> {
        return eonetApi.getCategories()
            .map { result ->
                ApiResult.Success(result.categories) as ApiResult<List<Category>>
            }
            .onErrorReturn { error ->
                ApiResult.Error(handleError(error))
            }
    }
}