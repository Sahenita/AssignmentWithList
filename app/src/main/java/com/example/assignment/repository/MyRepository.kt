package com.example.assignment.repository

import com.example.assignment.PicsumImage
import com.example.assignment.network.ApiService
import com.example.assignment.network.NetworkUtil
import retrofit2.Response

class MyRepository {

    suspend fun GetDataList(page:Int): List<PicsumImage>{
        val apiService = NetworkUtil.getInstance()?.getApi()

        // Make the network request using the Retrofit service
        return apiService?.getData(page)!!
    }
}