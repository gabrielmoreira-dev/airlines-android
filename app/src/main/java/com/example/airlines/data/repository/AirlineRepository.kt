package com.example.airlines.data.repository

import com.example.airlines.data.mapper.toDM
import com.example.airlines.data.remote.data_source.AirlineRDS
import com.example.domain.data_repository.AirlineDataRepository
import com.example.domain.model.Airline

class AirlineRepository(
    private val airlineRDS: AirlineRDS
): AirlineDataRepository {
    override suspend fun getAirlineList(): List<Airline> {
        return airlineRDS.getAirlineList().map { it.toDM() }
    }
}