package com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm

import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel

sealed class MvvmState {
    object Initial : MvvmState()

    object LoadingData : MvvmState()

    data class Clicked(
        val count: Int
    ) : MvvmState()

    data class ShowData(
        val listUsers: List<UserModel>
    ) : MvvmState()
}