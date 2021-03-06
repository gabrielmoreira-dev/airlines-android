package com.example.domain.use_case

import com.example.domain.AirlinesException

abstract class UseCase<P, R> {
    protected abstract suspend fun execute(params: P): R

    suspend fun invoke(params: P): R = try {
        execute(params)
    } catch (e: Exception) {
        if (e !is AirlinesException) {
            throw AirlinesException.UnexpectedException
        }
        throw e
    }
}