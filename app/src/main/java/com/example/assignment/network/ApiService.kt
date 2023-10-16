package com.example.assignment.network

import com.example.assignment.PicsumImage
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

     @GET("list")
    suspend fun getData(
        @Query("page") page: Int
       // @Query("limit") limit: Int
    ): List<PicsumImage>
}