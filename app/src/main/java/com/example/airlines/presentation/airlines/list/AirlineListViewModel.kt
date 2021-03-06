package com.example.airlines.presentation.airlines.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airlines.R
import com.example.airlines.presentation.airlines.list.models.AirlinePM
import com.example.airlines.presentation.common.AppDispatchers
import com.example.airlines.presentation.common.State
import com.example.airlines.presentation.common.UIString
import com.example.domain.model.Airline
import com.example.domain.use_case.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class AirlineListViewModel @Inject constructor(
    private val getAirlineListUC: UseCase<Unit, List<Airline>>,
    private val dispatcher: AppDispatchers
): ViewModel() {
    val state = MutableLiveData<State<List<AirlinePM>>>()

    fun getAirlineList() {
        state.postValue(State.Loading)

        val errorHandler = CoroutineExceptionHandler { _, _ ->
            state.postValue(State.Error(UIString.StringResource(R.string.generic_error_message)))
        }
        viewModelScope.launch(errorHandler) {
            val result = withContext(dispatcher.IO) { getAirlineListUC.invoke(Unit) }
            state.postValue(State.Success(result.map { it.toPM() }))
        }
    }
}