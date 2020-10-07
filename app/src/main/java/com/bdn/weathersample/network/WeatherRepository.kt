package com.bdn.weathersample.network

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import retrofit2.Response

class WeatherRepository {

    private lateinit var mContext: Context

    companion object {
        private val TAG: String = WeatherRepository::class.java.canonicalName as String


        @Volatile
        private var sTestSingleton: WeatherRepository? = null

        val WeatherRepositoryInstance: WeatherRepository?
            get() {
                if (sTestSingleton == null) {
                    /* Double check the locking method by synchronized block.*/
                    synchronized(WeatherRepository::class.java) {
                        if (sTestSingleton == null) {
                            sTestSingleton = WeatherRepository()
                        }
                    }
                }
                return sTestSingleton
            }
    }
    
    fun initBaseRepository(context: Application) {
        WeatherRepositoryInstance?.let {
        }
    }


    fun networkCall(
        url: String,
        headerMap: Map<String, String>,
        queryMap: Map<String, String>
    ) =
        liveData<NetworkStateHelper<JsonObject>>(Dispatchers.IO) {
            getResponse(
                this,
                url,
                headerMap,
                queryMap
            )
        }

    private suspend fun getResponse(
        liveDataScope: LiveDataScope<NetworkStateHelper<JsonObject>>,
        url: String,
        headerMap: Map<String, String>,
        queryMap: Map<String, String>
    ) {

            var networkHelper: NetworkStateHelper<JsonObject>
            var response: Response<JsonObject>? = null
            try {
                liveDataScope.emit(NetworkStateHelper.loading())
                response =
                    callApi(url, headerMap, queryMap)

                networkHelper = if (response.isSuccessful) {
                    NetworkStateHelper.success(response.body())
                } else {
                    getErrorMessage(response.errorBody())
                }
            }
            catch (e: Exception) {
                networkHelper = NetworkStateHelper.error(
                    e.localizedMessage ?: "ERROR_SOMETHING_WENT_WRONG"
                )
            }




                liveDataScope.emit(networkHelper)

        }

    private fun getErrorMessage(errorBody: ResponseBody?): NetworkStateHelper<JsonObject> {
        return NetworkStateHelper.error(
                        errorBody?.string()?:"Error"
                )
    }


    private suspend fun callApi(
        url: String,
        headerMap: Map<String, String>,
        queryMap: Map<String, String>
    ): Response<JsonObject> {
        val api = RetrofitService.createService(WeatherRetrofitService::class.java)

        return  api.getNetworkCall(url, headerMap, queryMap)

    }
}

data class NetworkStateHelper<out T>(val status: Status, val data: T?, val msg: String?) {
    companion object {
        fun <T> success(data: T?): NetworkStateHelper<T> {
            return NetworkStateHelper(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): NetworkStateHelper<T> {
            return NetworkStateHelper(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): NetworkStateHelper<T> {
            return NetworkStateHelper(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}