package com.example.airlines.data.remote.data_source

import com.example.airlines.data.remote.model.AirlineRM
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface AirlineRDS {
    @GET("/airlines")
    suspend fun getAirlineList(): List<AirlineRM>
}