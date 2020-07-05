package com.dhimasdewanto.androidpatterntemplates.core.mvi

interface IView<S: IState, I: IIntent> {
    fun render(state: S)
    fun sendIntent(intent: I)
}