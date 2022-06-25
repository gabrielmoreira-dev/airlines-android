package com.example.domain

sealed class AirlinesException: Exception() {
    class UnexpectedException: AirlinesException()
}