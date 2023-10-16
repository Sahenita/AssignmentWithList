package com.example.assignment.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkUtil private constructor() {

    // ... Existing code ...
  //  var BASE_URL = "https://picsum.photos/v2/"
    private var mRetrofit: Retrofit? = null
    val okHttpClientBuilder = OkHttpClient.Builder()
    var okHttpClient: OkHttpClient? = null
    var gson: Gson? = null
    companion object {
        private const val BASE_URL = "https://picsum.photos/v2/"

        @Volatile
        private var instance: NetworkUtil? = null

        fun getInstance(): NetworkUtil? {
            return instance ?: synchronized(this) {
                instance ?: NetworkUtil().also { instance = it }
            }
        }
    }

    fun getApi(): ApiService? {
        return mRetrofit?.create(ApiService::class.java)
    }

    init {
        // Initialize Retrofit and other configurations
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        okHttpClient = okHttpClientBuilder
            .addInterceptor(interceptor)
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build()
        gson = GsonBuilder()
            .setLenient()
            .create()

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}