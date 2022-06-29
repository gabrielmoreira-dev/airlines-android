package com.example.domain

sealed class AirlinesException: Exception() {
    object UnexpectedException: AirlinesException()
    object ServerException: AirlinesException()
}