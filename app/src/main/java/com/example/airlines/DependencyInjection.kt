package com.example.airlines

import com.example.airlines.data.remote.data_source.AirlineRDS
import com.example.airlines.data.remote.infrastructure.RetrofitClient
import com.example.airlines.data.repository.AirlineRepository
import com.example.domain.data_repository.AirlineDataRepository
import com.example.domain.model.Airline
import com.example.domain.use_case.GetAirlineListUC
import com.example.domain.use_case.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyInjection {
    @Provides
    @Singleton
    fun provideAirlineRDS(): AirlineRDS {
        return RetrofitClient
            .getInstance()
            .create(AirlineRDS::class.java)
    }

    @Provides
    @Singleton
    fun provideAirlineRepository(airlineRDS: AirlineRDS): AirlineDataRepository {
        return AirlineRepository(airlineRDS)
    }

    @Provides
    @Singleton
    fun provideGetAirlineListUC(
        airlineRepository: AirlineDataRepository
    ): UseCase<Unit, List<Airline>> {
        return GetAirlineListUC(airlineRepository)
    }
}