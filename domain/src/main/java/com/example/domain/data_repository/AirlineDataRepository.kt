package com.example.domain.data_repository

import com.example.domain.model.Airline

interface AirlineDataRepository {
    suspend fun getAirlineList(): List<Airline>
}