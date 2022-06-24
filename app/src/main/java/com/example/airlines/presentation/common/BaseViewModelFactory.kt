package com.example.airlines.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

abstract class BaseViewModelFactory<T : ViewModel>(
    private val instance: T
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(instance.javaClass)) {
            instance as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}