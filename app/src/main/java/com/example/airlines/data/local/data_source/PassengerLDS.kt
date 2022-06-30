package com.example.airlines.data.local.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.airlines.data.local.model.PassengerLM

@Dao
interface PassengerLDS {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassengerList(passengerList: List<PassengerLM>)

    @Query("SELECT * FROM passengers")
    suspend fun getAllPassengers(): List<PassengerLM>

    @Query("DELETE FROM passengers")
    suspend fun clearPassengerList()
}