package com.example.airlines.data.local.data_source

import androidx.room.*
import com.example.airlines.data.local.model.AirlineLM

@Dao
interface AirlineLDS {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAirlineList(airlineList: List<AirlineLM>)

    @Query("SELECT * FROM airlines")
    suspend fun getAllAirlines(): List<AirlineLM>

    @Query("DELETE FROM airlines")
    suspend fun clearAirlineList()
}