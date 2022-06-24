package com.example.airlines.data.remote.model

import com.example.airlines.presentation.airlines.list.models.AirlinePM
import com.google.gson.annotations.SerializedName

data class AirlineRM(
    val name: String,
    @SerializedName("logo_url") val logoUrl: String
)

fun AirlineRM.toPM() = AirlinePM(
    name = name ?: "",
    logoUrl = logoUrl ?: ""
)