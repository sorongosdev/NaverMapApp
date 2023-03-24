package com.sorongos.navermapapp

import android.content.res.Resources
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**어디서나 사용가능하게 객체 형식으로*/
object SearchRepository {

    // To create common header
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor()
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    /**interceptor은 interface이기 때문에 구현이 필요*/
    class AppInterceptor: Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            //push new info
            val clientId = Resources.getSystem().getString(R.string.naver_search_client_id)
            val clientSecret = Resources.getSystem().getString(R.string.naver_search_client_secret)
            val newRequest = chain.request().newBuilder()
                .addHeader("X-Naver-Client-Id",clientId) //client id
                .addHeader("X-Naver-Client-Secret",clientSecret) //client secret
                .build()
            chain.proceed(newRequest)
        }

    }
}