package com.example.airlines.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airlines")
data class AirlineLM(
    val name: String,
    val logoUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
