package com.example.airlines.data.repository

import com.example.airlines.data.mapper.toDM
import com.example.airlines.data.remote.data_source.AirlineRDS
import com.example.airlines.data.remote.model.AirlineRM
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

    private val airlineRMList = arrayListOf(
        AirlineRM("Airline 1", "http://test.com/logo"),
        AirlineRM("Airline 2", "http://test.com/logo"),
        AirlineRM("Airline 3", "http://test.com/logo")
    )

    @Before
    fun setup() {
        sut = AirlineRepository(airlineRDS)
    }

    @Test
    fun `when remote data source returns AirlineRM list should return airline list`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenReturn(airlineRMList)

        val result = sut.getAirlineList()
        val airlineList = airlineRMList.map { it.toDM() }

        assertThat(result).isEqualTo(airlineList)
    }

    @Test
    fun `when remote data source returns empty list should return empty list`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenReturn(arrayListOf())

        val result = sut.getAirlineList()

        assertThat(result).isEqualTo(arrayListOf<Airline>())
    }

    @Test
    fun `when remote data source throws error should rethrow`() = runTest {
        whenever(airlineRDS.getAirlineList()).thenThrow(RuntimeException())
        var exception: Exception? = null

        try {
            sut.getAirlineList()
        } catch (e: Exception) {
            exception = e
        }

        assertThat(exception).isInstanceOf(Exception::class.java)
    }
}