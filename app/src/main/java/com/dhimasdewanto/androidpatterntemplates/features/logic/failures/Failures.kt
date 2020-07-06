package com.dhimasdewanto.androidpatterntemplates.features.logic.failures

import com.dhimasdewanto.androidpatterntemplates.core.error_handler.IFailure

sealed class Failures : IFailure {
    object SomeFailure : Failures()
    object ExampleFailure : Failures()
}