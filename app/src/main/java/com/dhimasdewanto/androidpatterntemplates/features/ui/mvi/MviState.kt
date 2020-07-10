package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.mvi.IState
import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel

sealed class MviState : IState {
    object Initial : MviState()

    object LoadingData : MviState()

    data class Clicked(
        val count: Int
    ) : MviState()

    data class ShowData(
        val listUsers: List<UserModel>
    ) : MviState()

    data class ShowError(
        val message: String
    ) : MviState()
}