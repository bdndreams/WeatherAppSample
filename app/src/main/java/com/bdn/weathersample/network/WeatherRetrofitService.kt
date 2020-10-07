package com.bdn.weathersample.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface WeatherRetrofitService {

    @GET
    suspend fun getNetworkCall(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>,
        @QueryMap requestParameters: Map<String, String>
    ): Response<JsonObject>
}