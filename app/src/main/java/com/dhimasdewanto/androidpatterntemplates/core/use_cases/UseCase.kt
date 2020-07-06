package com.dhimasdewanto.androidpatterntemplates.core.use_cases

import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Err
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.IFailure
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Ok
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Res

interface UseCase<Type, Params, Failure : IFailure> {
    /**
     * Execute method with validation.
     */
    suspend fun call(params: Params): Res<Type, Failure> {
        return when (val isValid = validate(params)) {
            is Ok -> execute(params)
            is Err -> isValid
        }
    }

    /**
     * Execute method without validation.
     */
    suspend fun execute(params: Params): Res<Type, Failure>

    /**
     * Validation of this use case.
     */
    suspend fun validate(params: Params): Res<Unit, Failure>
}
