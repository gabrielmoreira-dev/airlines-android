package com.example.domain.use_case

import com.example.domain.data_repository.AirlineDataRepository
import com.example.domain.model.Airline

class GetAirlineListUC(
    private val airlineRepository: AirlineDataRepository
): UseCase<Unit, List<Airline>>() {
    override suspend fun execute(params: Unit): List<Airline> {
        return airlineRepository.getAirlineList()
    }
}