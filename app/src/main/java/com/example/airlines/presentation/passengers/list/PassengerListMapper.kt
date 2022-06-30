package com.example.airlines.presentation.passengers.list

import com.example.airlines.R
import com.example.airlines.presentation.common.UIString
import com.example.airlines.presentation.passengers.list.models.PassengerPM
import com.example.domain.model.Passenger

fun Passenger.toPM() = PassengerPM(
    name = name,
    photoUrl = photoUrl,
    age = UIString.StringResource(R.string.passenger_age, age)
)