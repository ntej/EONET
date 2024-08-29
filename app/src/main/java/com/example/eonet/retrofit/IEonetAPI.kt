package com.example.eonet.retrofit

import com.example.eonet.model.Categories
import com.example.eonet.model.CategoryData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IEonetAPI {
   @GET("categories")
   fun getCategories():Observable<Categories>

   @GET("{categoryName}")
   fun category(@Path("categoryName") categoryName:String, @Query("days") forLastDays: Int):Observable<CategoryData>
}