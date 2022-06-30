package com.example.airlines.data.mapper

import com.example.airlines.data.local.model.PassengerLM
import com.example.airlines.data.remote.model.PassengerRM
import com.example.domain.model.Passenger

fun PassengerRM.toLM() = PassengerLM(name = name, photoUrl = photoUrl, age = age)

fun PassengerLM.toDM() = Passenger(name = name, photoUrl = photoUrl, age = age)