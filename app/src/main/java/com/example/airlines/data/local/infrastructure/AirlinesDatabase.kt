package com.example.airlines.data.local.infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.airlines.data.local.data_source.AirlineLDS
import com.example.airlines.data.local.data_source.PassengerLDS
import com.example.airlines.data.local.model.AirlineLM
import com.example.airlines.data.local.model.PassengerLM

@Database(
    entities = [AirlineLM::class, PassengerLM::class],
    version = 1
)
abstract class AirlinesDatabase: RoomDatabase() {
    abstract fun getAirlineLDS(): AirlineLDS

    abstract fun getPassengerLDS(): PassengerLDS
}