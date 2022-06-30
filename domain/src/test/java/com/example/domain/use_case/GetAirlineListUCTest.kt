package com.example.domain.use_case

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.domain.AirlinesException
import com.example.domain.data_repository.AirlineDataRepository
import com.example.domain.model.Airline
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetAirlineListUCTest {
    private lateinit var sut: GetAirlineListUC
    private val airlineRepository = mock<AirlineDataRepository>()

    private val fixture = kotlinFixture()
    private val airlineList = fixture<List<Airline>>()

    @Before
    fun setup() {
        sut = GetAirlineListUC(airlineRepository)
    }

    @Test
    fun `when repository returns airline list should return airline list`() = runTest {
        whenever(airlineRepository.getAirlineList()).thenReturn(airlineList)

        val result = sut.invoke(Unit)

        assertThat(result).isEqualTo(airlineList)
    }

    @Test
    fun `when repository returns empty list should return empty list`() = runTest {
        whenever(airlineRepository.getAirlineList()).thenReturn(arrayListOf())

        val result = sut.invoke(Unit)

        assertThat(result).isEqualTo(arrayListOf<Airline>())
    }

    @Test
    fun `when repository throws an exception should rethrow`() = runTest {
        whenever(airlineRepository.getAirlineList()).thenThrow(RuntimeException())
        var exception: AirlinesException? = null

        try {
            sut.invoke(Unit)
        } catch (e: AirlinesException) {
            exception = e
        }

        assertThat(exception).isInstanceOf(AirlinesException.UnexpectedException::class.java)
    }
}