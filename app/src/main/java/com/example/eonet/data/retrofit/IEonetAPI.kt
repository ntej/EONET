package com.example.eonet.data.retrofit

import com.example.eonet.entities.Categories
import com.example.eonet.entities.CategoryData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IEonetAPI {
   @GET("categories")
   fun getCategories():Single<Categories>

   @GET("categories/{categoryName}")
   fun getCategory(@Path("categoryName") categoryName:String):Single<CategoryData>
}