package com.example.domain.use_case

import com.example.domain.data_repository.AirlineDataRepository

class GetAirlineListUC(private val airlineRepository: AirlineDataRepository) {
    suspend fun invoke() = airlineRepository.getAirlineList()
}