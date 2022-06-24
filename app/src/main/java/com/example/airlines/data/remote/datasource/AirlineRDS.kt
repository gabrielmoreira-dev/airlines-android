package com.example.airlines.data.remote.datasource

import com.example.airlines.data.remote.model.AirlineRM
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface AirlineRDS {
    @GET("/airlines")
    fun getAirlineList(): Call<List<AirlineRM>>

    companion object {
        private val remoteDataSource by lazy {
            val service = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service.create(AirlineRDS::class.java)
        }

        fun getInstance(): AirlineRDS = remoteDataSource
    }
}