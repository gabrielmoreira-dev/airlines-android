package com.example.airlines.data.remote.data_source

import com.example.airlines.data.remote.model.PassengerRM
import retrofit2.http.GET

interface PassengerRDS {
    @GET("/passengers")
    suspend fun getPassengerList(): List<PassengerRM>
}