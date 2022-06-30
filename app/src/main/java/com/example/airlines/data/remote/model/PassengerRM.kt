package com.example.airlines.data.remote.model

import com.google.gson.annotations.SerializedName

data class PassengerRM(
    val name: String,
    @SerializedName("photo_url")
    val photoUrl: String,
    val age: Int
)
