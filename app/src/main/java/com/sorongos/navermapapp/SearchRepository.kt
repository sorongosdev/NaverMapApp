package com.sorongos.navermapapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**어디서나 사용가능하게 객체 형식으로*/
object SearchRepository {

    // To create common header
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AppInterceptor())
        .build()

    /**kotlin과 json사이의 어댑터*/
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    private  val service = retrofit.create(SearchService::class.java)

    /**뷰에서 받아온 쿼리*/
    fun getGoodRestaurant(query: String): Call<SearchResult>{
        return service.getGoodRestaurant(query = "$query 마라탕", display = 5)
    }

    /**interceptor은 interface이기 때문에 구현이 필요*/
    class AppInterceptor: Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            //push new info
            val clientId = MyApplication.applicationContext.getString(R.string.naver_search_client_id)
            val clientSecret = MyApplication.applicationContext.getString(R.string.naver_search_client_secret)
            val newRequest = chain.request().newBuilder()
                .addHeader("X-Naver-Client-Id",clientId) //client id
                .addHeader("X-Naver-Client-Secret",clientSecret) //client secret
                .build()
            return chain.proceed(newRequest)
        }
    }
}