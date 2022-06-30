package com.example.airlines.data.repository

import com.example.airlines.data.local.data_source.PassengerLDS
import com.example.airlines.data.local.model.PassengerLM
import com.example.airlines.data.mapper.toDM
import com.example.airlines.data.mapper.toLM
import com.example.airlines.data.remote.data_source.PassengerRDS
import com.example.domain.AirlinesException
import com.example.domain.data_repository.PassengerDataRepository
import com.example.domain.model.Passenger

class PassengerRepository(
    private val passengerRDS: PassengerRDS,
    private val passengerLDS: PassengerLDS
): PassengerDataRepository {
    override suspend fun getPassengerList(): List<Passenger> = try {
        getRemotePassengerList()
            .map { it.toLM() }.apply { insertLocalPassengerList(this) }
            .map { it.toDM() }.apply { return this }
    } catch (e: Exception) {
        getLocalPassengerList().map { it.toDM() }.apply { return this }
    }

    private suspend fun getRemotePassengerList() = passengerRDS.getPassengerList()

    private suspend fun insertLocalPassengerList(
        passengerList: List<PassengerLM>
    ) = passengerLDS.apply {
        clearPassengerList()
        insertPassengerList(passengerList)
    }

    private suspend fun getLocalPassengerList() = passengerLDS.getAllPassengers().apply {
        if (isEmpty()) throw AirlinesException.ServerException
        return this
    }
}