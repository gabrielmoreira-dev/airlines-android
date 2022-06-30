package com.example.airlines

import android.content.Context
import androidx.room.Room
import com.example.airlines.data.local.data_source.AirlineLDS
import com.example.airlines.data.local.data_source.PassengerLDS
import com.example.airlines.data.local.infrastructure.AirlinesDatabase
import com.example.airlines.data.remote.data_source.AirlineRDS
import com.example.airlines.data.remote.data_source.PassengerRDS
import com.example.airlines.data.remote.infrastructure.RetrofitClient
import com.example.airlines.data.repository.AirlineRepository
import com.example.airlines.data.repository.PassengerRepository
import com.example.airlines.presentation.common.AppDispatchers
import com.example.domain.data_repository.AirlineDataRepository
import com.example.domain.data_repository.PassengerDataRepository
import com.example.domain.model.Airline
import com.example.domain.model.Passenger
import com.example.domain.use_case.GetAirlineListUC
import com.example.domain.use_case.GetPassengerListUC
import com.example.domain.use_case.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyInjection {
    @Provides
    @Singleton
    fun provideAirlineRDS(): AirlineRDS {
        return RetrofitClient.getInstance().create(AirlineRDS::class.java)
    }

    @Provides
    @Singleton
    fun providePassengerRDS(): PassengerRDS {
        return RetrofitClient.getInstance().create(PassengerRDS::class.java)
    }

    @Provides
    @Singleton
    fun provideAirlineDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AirlinesDatabase::class.java, "airlines").build()

    @Provides
    @Singleton
    fun provideAirlineLDS(database: AirlinesDatabase) = database.getAirlineLDS()


    @Provides
    @Singleton
    fun providePassengerLDS(database: AirlinesDatabase) = database.getPassengerLDS()

    @Provides
    @Singleton
    fun provideAirlineRepository(
        airlineRDS: AirlineRDS,
        airlineLDS: AirlineLDS
    ): AirlineDataRepository {
        return AirlineRepository(airlineRDS, airlineLDS)
    }

    @Provides
    @Singleton
    fun providePassengerRepository(
        passengerRDS: PassengerRDS,
        passengerLDS: PassengerLDS
    ): PassengerDataRepository {
        return PassengerRepository(passengerRDS, passengerLDS)
    }

    @Provides
    @Singleton
    fun provideGetAirlineListUC(
        airlineRepository: AirlineDataRepository
    ): UseCase<Unit, List<Airline>> {
        return GetAirlineListUC(airlineRepository)
    }

    @Provides
    @Singleton
    fun provideGetPassengerListUC(
        passengerRepository: PassengerDataRepository
    ): UseCase<Unit, List<Passenger>> {
        return GetPassengerListUC(passengerRepository)
    }

    @Provides
    @Singleton
    fun provideAppDispatchers() = AppDispatchers()
}