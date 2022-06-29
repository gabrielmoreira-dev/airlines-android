package com.example.airlines.data.repository

import com.example.airlines.data.local.data_source.AirlineLDS
import com.example.airlines.data.local.model.AirlineLM
import com.example.airlines.data.mapper.toDM
import com.example.airlines.data.mapper.toLM
import com.example.airlines.data.remote.data_source.AirlineRDS
import com.example.domain.AirlinesException
import com.example.domain.data_repository.AirlineDataRepository
import com.example.domain.model.Airline

class AirlineRepository(
    private val airlineRDS: AirlineRDS,
    private val airlineLDS: AirlineLDS
) : AirlineDataRepository {
    override suspend fun getAirlineList(): List<Airline> = try {
        getRemoteAirlineList()
            .map { it.toLM() }.apply { insertLocalAirlineList(this) }
            .map { it.toDM() }.apply { return this }
    } catch (e: Exception) {
        getLocalAirlineList().map { it.toDM() }.apply { return this }
    }

    private suspend fun getRemoteAirlineList() = airlineRDS.getAirlineList()

    private suspend fun insertLocalAirlineList(airlineList: List<AirlineLM>) = airlineLDS.apply {
        clearAirlineList()
        insertAirlineList(airlineList)
    }

    private suspend fun getLocalAirlineList() = airlineLDS.getAllAirlines().apply {
        if (isEmpty()) throw AirlinesException.ServerException
        return this
    }
}