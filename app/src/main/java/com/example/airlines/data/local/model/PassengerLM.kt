package com.example.airlines.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passengers")
data class PassengerLM(
    val name: String,
    val photoUrl: String,
    val age: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
