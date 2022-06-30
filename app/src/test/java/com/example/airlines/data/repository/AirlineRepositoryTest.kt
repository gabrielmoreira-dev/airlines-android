package com.example.airlines.data.repository

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.airlines.data.local.data_source.AirlineLDS
import com.example.airlines.data.local.model.AirlineLM
import com.example.airlines.data.mapper.toDM
import com.example.airlines.data.mapper.toLM
import com.example.airlines.data.remote.data_source.AirlineRDS
import com.example.airlines.data.remote.model.AirlineRM
import com.example.domain.AirlinesException
import com.example.domain.model.Airline
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AirlineRepositoryTest {
    private lateinit var sut: AirlineRepository
    private val airlineRDS = mock<AirlineRDS>()
    private val airlineLDS = mock<AirlineLDS>()

    private val fixture = kotlinFixture()
    private val airlineRMList = fixture<List<AirlineRM>>()
    private val airlineLMList = fixture<List<AirlineLM>>()

    @Before
    fun setup() {
        sut = AirlineRepository(airlineRDS, airlineLDS)
    }

    @Test
    fun `when remote data source returns AirlineRM list should return airline list`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenReturn(airlineRMList)

        val result = sut.getAirlineList()
        val airlineList = airlineRMList.map { it.toLM().toDM() }

        assertThat(result).isEqualTo(airlineList)
    }

    @Test
    fun `when remote data source returns empty list should return empty list`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenReturn(arrayListOf())

        val result = sut.getAirlineList()

        assertThat(result).isEqualTo(arrayListOf<Airline>())
    }

    @Test
    fun `when remote data source throws error should load local data`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenThrow(RuntimeException())
        whenever(airlineLDS.getAllAirlines()).thenReturn(airlineLMList)

        val result = sut.getAirlineList()
        val airlineList = airlineLMList.map { it.toDM() }

        assertThat(result).isEqualTo(airlineList)
    }

    @Test
    fun `when local data source is empty should throw exception`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenThrow(RuntimeException())
        whenever(airlineLDS.getAllAirlines()).thenReturn(arrayListOf())
        var exception: Exception? = null

        try {
            sut.getAirlineList()
        } catch (e: Exception) {
            exception = e
        }

        assertThat(exception).isInstanceOf(AirlinesException.ServerException::class.java)
    }

    @Test
    fun `when local data source throws exception should rethrow`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenThrow(RuntimeException())
        whenever(airlineLDS.getAllAirlines()).thenThrow(RuntimeException())
        var exception: Exception? = null

        try {
            sut.getAirlineList()
        } catch (e: Exception) {
            exception = e
        }

        assertThat(exception).isInstanceOf(RuntimeException::class.java)
    }
}