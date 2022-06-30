package com.example.airlines.data.mapper

import com.example.airlines.data.local.model.AirlineLM
import com.example.airlines.data.remote.model.AirlineRM
import com.example.domain.model.Airline

fun AirlineRM.toLM() = AirlineLM(name = name, logoUrl = logoUrl)

fun AirlineLM.toDM() = Airline(name = name, logoUrl = logoUrl)