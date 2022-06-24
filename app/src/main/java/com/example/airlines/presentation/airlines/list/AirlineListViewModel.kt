package com.example.airlines.presentation.airlines.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airlines.data.remote.model.AirlineRM
import com.example.airlines.data.remote.model.toPM
import com.example.airlines.data.repository.AirlineRepository
import com.example.airlines.presentation.airlines.list.models.AirlinePM
import com.example.airlines.presentation.common.BaseViewModelFactory
import com.example.airlines.presentation.common.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirlineListViewModelFactory(
    repository: AirlineRepository
) : BaseViewModelFactory<AirlineListViewModel>(
    AirlineListViewModel(repository)
)

class AirlineListViewModel(
    private val repository: AirlineRepository
): ViewModel() {
    val state = MutableLiveData<State<List<AirlinePM>>>()

    fun getAirlineList() {
        state.postValue(State.Loading)

        val request = repository.getAirlineList()
        request.enqueue(object : Callback<List<AirlineRM>> {
            override fun onResponse(
                call: Call<List<AirlineRM>>,
                response: Response<List<AirlineRM>>
            ) {
                if(response.code() == 200) {
                    response.body()?.let {
                        state.postValue(State.Success(it.map {
                             it.toPM()
                        }))
                    }
                } else {
                    state.postValue(State.Error)
                }
            }

            override fun onFailure(call: Call<List<AirlineRM>>, t: Throwable) {
                state.postValue(State.Error)
            }
        })
    }
}