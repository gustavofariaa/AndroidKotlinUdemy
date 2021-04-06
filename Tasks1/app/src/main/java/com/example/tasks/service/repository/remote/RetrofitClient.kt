package com.example.tasks.service.repository.remote

import com.example.tasks.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private const val BASE_URL = "http://devmasterteam.com/CursoAndroidAPI/"
        private lateinit var retrofit: Retrofit

        private var mPersonKey = ""
        private var mTokenKey = ""

        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(TaskConstants.HEADER.PERSON_KEY, mPersonKey)
                    .addHeader(TaskConstants.HEADER.TOKEN_KEY, mTokenKey)
                    .build()
                chain.proceed(request)
            }
            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun addHeader(personKey: String, token: String) {
            mPersonKey = personKey
            mTokenKey = token
        }

        fun <T> createService(serviceClass: Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }
    }

}