package com.example.airlines.data.repository

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.airlines.data.local.data_source.PassengerLDS
import com.example.airlines.data.local.model.PassengerLM
import com.example.airlines.data.mapper.toDM
import com.example.airlines.data.mapper.toLM
import com.example.airlines.data.remote.data_source.PassengerRDS
import com.example.airlines.data.remote.model.PassengerRM
import com.example.domain.AirlinesException
import com.example.domain.model.Passenger
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PassengerRepositoryTest {
    private lateinit var sut: PassengerRepository
    private val passengerRDS = mock<PassengerRDS>()
    private val passengerLDS = mock<PassengerLDS>()

    private val fixture = kotlinFixture()
    private val passengerRMList = fixture<List<PassengerRM>>()
    private val passengerLMList = fixture<List<PassengerLM>>()

    @Before
    fun setup() {
        sut = PassengerRepository(passengerRDS, passengerLDS)
    }

    @Test
    fun `when remote data source returns PassengerRM list should return passenger list`() = runTest {
        whenever(passengerRDS.getPassengerList()).thenReturn(passengerRMList)

        val result = sut.getPassengerList()
        val passengerList = passengerRMList.map { it.toLM().toDM() }

        Truth.assertThat(result).isEqualTo(passengerList)
    }

    @Test
    fun `when remote data source returns empty list should return empty list`() = runTest {
        whenever(passengerRDS.getPassengerList()).thenReturn(arrayListOf())

        val result = sut.getPassengerList()

        Truth.assertThat(result).isEqualTo(arrayListOf<Passenger>())
    }

    @Test
    fun `when remote data source throws error should load local data`() = runTest {
        whenever(passengerRDS.getPassengerList()).thenThrow(RuntimeException())
        whenever(passengerLDS.getAllPassengers()).thenReturn(passengerLMList)

        val result = sut.getPassengerList()
        val passengerList = passengerLMList.map { it.toDM() }

        Truth.assertThat(result).isEqualTo(passengerList)
    }

    @Test
    fun `when local data source is empty should throw exception`() = runTest {
        whenever(passengerRDS.getPassengerList()).thenThrow(RuntimeException())
        whenever(passengerLDS.getAllPassengers()).thenReturn(arrayListOf())
        var exception: Exception? = null

        try {
            sut.getPassengerList()
        } catch (e: Exception) {
            exception = e
        }

        Truth.assertThat(exception).isInstanceOf(AirlinesException.ServerException::class.java)
    }

    @Test
    fun `when local data source throws exception should rethrow`() = runTest {
        whenever(passengerRDS.getPassengerList()).thenThrow(RuntimeException())
        whenever(passengerLDS.getAllPassengers()).thenThrow(RuntimeException())
        var exception: Exception? = null

        try {
            sut.getPassengerList()
        } catch (e: Exception) {
            exception = e
        }

        Truth.assertThat(exception).isInstanceOf(RuntimeException::class.java)
    }
}