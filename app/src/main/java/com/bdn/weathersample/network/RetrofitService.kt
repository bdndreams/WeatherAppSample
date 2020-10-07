package com.bdn.weathersample.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit service
 * Provides retrofit object for API call
 */
object RetrofitService {

    private var client = OkHttpClient.Builder()

    init {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(logging)
        client.connectTimeout(60, TimeUnit.SECONDS) //Connection timeout
            .readTimeout(60, TimeUnit.SECONDS) //Socket timeout
    }


    fun <S> createService(serviceClass: Class<S>): S {
        return getRetrofit(client.build()).create(serviceClass)
    }

    private fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}