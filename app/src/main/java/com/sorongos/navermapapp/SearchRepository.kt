package com.sorongos.navermapapp

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**어디서나 사용가능하게 객체 형식으로*/
object SearchRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}