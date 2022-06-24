package com.example.airlines.data.repository

import com.example.airlines.data.remote.datasource.AirlineRDS

class AirlineRepository(private val airlineRDS: AirlineRDS) {
    fun getAirlineList() = airlineRDS.getAirlineList()
}