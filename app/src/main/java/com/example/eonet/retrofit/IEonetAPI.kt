package com.example.eonet.retrofit

import com.example.eonet.retrofit.model.Categories
import com.example.eonet.retrofit.model.CategoryData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IEonetAPI {
   @GET("categories")
   fun getCategories():Observable<Categories>

   @GET("categories/{categoryName}")
   fun getCategory(@Path("categoryName") categoryName:String):Observable<CategoryData>
}