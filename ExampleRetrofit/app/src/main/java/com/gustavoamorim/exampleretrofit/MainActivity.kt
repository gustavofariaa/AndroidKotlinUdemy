package com.gustavoamorim.exampleretrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val mRemote = RetrofitClient.createService(PostService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call: Call<List<PostModel>> = mRemote.list()
        val response = call.enqueue(object : Callback<List<PostModel>> {

            override fun onFailure(call: Call<List<PostModel>>, throwable: Throwable) {
                val fail = throwable.message
            }

            override fun onResponse(
                call: Call<List<PostModel>>, response: Response<List<PostModel>>
            ) {
                val body = response.body()
            }

        })
    }

}