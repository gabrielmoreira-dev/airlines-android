package com.example.airlines.presentation.airlines.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmattus.kotlinfixture.kotlinFixture
import com.example.airlines.MainCoroutineRule
import com.example.airlines.presentation.airlines.list.models.AirlinePM
import com.example.airlines.presentation.common.AppDispatchers
import com.example.airlines.presentation.common.State
import com.example.domain.model.Airline
import com.example.domain.use_case.GetAirlineListUC
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AirlineListViewModelStateSpy {
    sealed class Message {
        data class SuccessStateReceived(val airlineLst: List<AirlinePM>): Message()
        object LoadingStateReceived: Message()
        object ErrorStateReceived: Message()
    }

    val messageList = arrayListOf<Message>()

    fun addState(state: State<List<AirlinePM>>) {
        when (state) {
            is State.Success -> messageList.add(Message.SuccessStateReceived(state.model))
            is State.Loading -> messageList.add(Message.LoadingStateReceived)
            is State.Error -> messageList.add(Message.ErrorStateReceived)
        }
    }
}

@ExperimentalCoroutinesApi
class AirlineListViewModelTest {
    private lateinit var sut: AirlineListViewModel
    private val getAirlineListUC = mock<GetAirlineListUC>()
    private val viewModelStateSpy = AirlineListViewModelStateSpy()

    private val fixture = kotlinFixture()
    private val airlineList = fixture<List<Airline>>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        val dispatcher = AppDispatchers(IO = UnconfinedTestDispatcher())
        sut = AirlineListViewModel(getAirlineListUC, dispatcher)
        sut.state.observeForever {
            viewModelStateSpy.addState(it)
        }
    }

    @Test
    fun `when use case returns airline list should set success state`() = runTest {
        whenever(getAirlineListUC.invoke(Unit)).thenReturn(airlineList)

        sut.getAirlineList()
        val airlinePMList = airlineList.map { it.toPM() }

        assertThat(viewModelStateSpy.messageList).isEqualTo(arrayListOf(
            AirlineListViewModelStateSpy.Message.LoadingStateReceived,
            AirlineListViewModelStateSpy.Message.SuccessStateReceived(airlinePMList)
        ))
    }

    @Test
    fun `when use case returns empty list should set success state`() = runTest {
        whenever(getAirlineListUC.invoke(Unit)).thenReturn(arrayListOf())

        sut.getAirlineList()

        assertThat(viewModelStateSpy.messageList).isEqualTo(arrayListOf(
            AirlineListViewModelStateSpy.Message.LoadingStateReceived,
            AirlineListViewModelStateSpy.Message.SuccessStateReceived(arrayListOf<AirlinePM>())
        ))
    }

    @Test
    fun `when use case throws an exception should set error state`() = runTest {
        whenever(getAirlineListUC.invoke(Unit)).thenThrow(RuntimeException())

        sut.getAirlineList()

        assertThat(viewModelStateSpy.messageList).isEqualTo(arrayListOf(
            AirlineListViewModelStateSpy.Message.LoadingStateReceived,
            AirlineListViewModelStateSpy.Message.ErrorStateReceived
        ))
    }
}