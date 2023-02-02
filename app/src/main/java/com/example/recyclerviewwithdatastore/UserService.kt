package com.example.recyclerviewwithdatastore

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "" +
        ""

object UserService {
    val userInstance: UserInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userInstance = retrofit.create(UserInterface::class.java)
    }

    private fun getRetrofitClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor())
//            .addInterceptor { chain ->
//                val builder = chain.request().newBuilder()
//                return@addInterceptor chain.proceed(builder.build())
//            }
//
//          used to add header -> queries in url -> eg: india || page
            .build()
    }

    private fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}