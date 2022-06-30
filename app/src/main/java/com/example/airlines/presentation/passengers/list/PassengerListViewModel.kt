package com.example.airlines.presentation.passengers.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airlines.R
import com.example.airlines.presentation.common.AppDispatchers
import com.example.airlines.presentation.common.State
import com.example.airlines.presentation.common.UIString
import com.example.airlines.presentation.passengers.list.models.PassengerPM
import com.example.domain.model.Passenger
import com.example.domain.use_case.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PassengerListViewModel @Inject constructor(
    private val getPassengerListUC: UseCase<Unit, List<Passenger>>,
    private val dispatcher: AppDispatchers
) : ViewModel() {
    val state = MutableLiveData<State<List<PassengerPM>>>()

    fun getPassengerList() {
        state.postValue(State.Loading)

        val errorHandler = CoroutineExceptionHandler { _, _ ->
            state.postValue(State.Error(UIString.StringResource(R.string.generic_error_message)))
        }
        viewModelScope.launch(errorHandler) {
            val result = withContext(dispatcher.IO) { getPassengerListUC.invoke(Unit) }
            state.postValue(State.Success(result.map { it.toPM() }))
        }
    }
}