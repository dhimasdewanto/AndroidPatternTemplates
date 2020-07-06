package com.dhimasdewanto.androidpatterntemplates.core.error_handler

sealed class Res<out T, out E : IFailure>
class Ok<out T>(val value: T) : Res<T, Nothing>()
class Err<out E : IFailure>(val failure: E) : Res<Nothing, E>()