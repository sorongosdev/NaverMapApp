package com.sorongos.navermapapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**쿼리를 받는 서비스*/
interface SearchService {

    @GET("v1/search/local.json")
    fun getGoodRestaurant(
        @Query("query") query: String,
        @Query("display") display: Int, // 표시되는 개수
    ): Call<SearchResult>
}