package com.example.domain.use_case

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.domain.AirlinesException
import com.example.domain.data_repository.PassengerDataRepository
import com.example.domain.model.Passenger
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetPassengerListUCTest {
    private lateinit var sut: GetPassengerListUC
    private val passengerRepository = mock<PassengerDataRepository>()

    private val fixture = kotlinFixture()
    private val passengerList = fixture<List<Passenger>>()

    @Before
    fun setup() {
        sut = GetPassengerListUC(passengerRepository)
    }

    @Test
    fun `when repository returns passenger list should return passenger list`() = runTest {
        whenever(passengerRepository.getPassengerList()).thenReturn(passengerList)

        val result = sut.invoke(Unit)

        Truth.assertThat(result).isEqualTo(passengerList)
    }

    @Test
    fun `when repository returns empty list should return empty list`() = runTest {
        whenever(passengerRepository.getPassengerList()).thenReturn(arrayListOf())

        val result = sut.invoke(Unit)

        Truth.assertThat(result).isEqualTo(arrayListOf<Passenger>())
    }

    @Test
    fun `when repository throws an exception should rethrow`() = runTest {
        whenever(passengerRepository.getPassengerList()).thenThrow(RuntimeException())
        var exception: AirlinesException? = null

        try {
            sut.invoke(Unit)
        } catch (e: AirlinesException) {
            exception = e
        }

        Truth.assertThat(exception).isInstanceOf(AirlinesException.UnexpectedException::class.java)
    }
}