package com.example.airlines.presentation.airlines.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airlines.presentation.airlines.list.models.AirlinePM
import com.example.airlines.presentation.common.State
import com.example.domain.use_case.GetAirlineListUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AirlineListViewModel @Inject constructor(
    private val getAirlineList: GetAirlineListUC
): ViewModel() {
    val state = MutableLiveData<State<List<AirlinePM>>>()

    fun getAirlineList() {
        state.postValue(State.Loading)

        CoroutineScope(Dispatchers.IO).launch {
            val result = getAirlineList.invoke()

            withContext(Dispatchers.Main) {
                state.postValue(State.Success(result.map { it.toPM() }))
            }
        }
    }
}