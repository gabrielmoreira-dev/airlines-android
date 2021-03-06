package com.example.airlines.presentation.common

sealed interface State<out T: Any> {
    data class Success<out T: Any>(val model: T): State<T>
    object Loading : State<Nothing>
    data class Error(val message: UIString) : State<Nothing>
}