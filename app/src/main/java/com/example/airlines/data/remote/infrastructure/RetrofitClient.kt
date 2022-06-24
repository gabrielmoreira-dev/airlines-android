package com.example.airlines.data.remote.infrastructure

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private val client by lazy {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getInstance(): Retrofit = client
    }
}