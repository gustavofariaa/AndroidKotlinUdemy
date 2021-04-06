package com.example.tasks.service.repository.remote

import com.example.tasks.service.repository.model.PriorityModel
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface PriorityService {

    @GET("Priority")
    fun all(): Call<List<PriorityModel>>

}