package com.example.airlines.data.remote.model

import com.google.gson.annotations.SerializedName

data class AirlineRM(
    val name: String,

    @SerializedName("logo_url")
    val logoUrl: String
)