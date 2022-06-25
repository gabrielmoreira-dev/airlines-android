package com.example.airlines.data.mapper

import com.example.airlines.data.remote.model.AirlineRM
import com.example.domain.model.Airline

fun AirlineRM.toDM() = Airline(name = name, logoUrl = logoUrl)