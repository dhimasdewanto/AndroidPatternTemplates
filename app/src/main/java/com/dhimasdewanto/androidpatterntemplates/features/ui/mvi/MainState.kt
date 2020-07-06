package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.mvi.IState
import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel

sealed class MainState : IState {
    object Initial : MainState()

    object LoadingData : MainState()

    data class Clicked(
        val count: Int
    ) : MainState()

    data class ShowData(
        val listUsers: List<UserModel>
    ) : MainState()

    data class ShowError(
        val message: String
    ) : MainState()
}