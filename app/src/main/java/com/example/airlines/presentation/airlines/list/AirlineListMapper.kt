package com.example.airlines.presentation.airlines.list

import com.example.airlines.presentation.airlines.list.models.AirlinePM
import com.example.domain.model.Airline

fun Airline.toPM() = AirlinePM(name = name, logoUrl = logoUrl)