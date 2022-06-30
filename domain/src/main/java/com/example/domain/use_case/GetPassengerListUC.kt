package com.example.domain.use_case

import com.example.domain.data_repository.PassengerDataRepository
import com.example.domain.model.Passenger

class GetPassengerListUC(
    private val passengerRepository: PassengerDataRepository
): UseCase<Unit, List<Passenger>>() {
    override suspend fun execute(params: Unit): List<Passenger> {
        return passengerRepository.getPassengerList()
    }
}