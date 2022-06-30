package com.example.domain.data_repository

import com.example.domain.model.Passenger

interface PassengerDataRepository {
    suspend fun getPassengerList(): List<Passenger>
}