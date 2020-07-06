package com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm

import com.dhimasdewanto.androidpatterntemplates.core.mvvm.ScopeViewModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.repositories.UserRepo

class MvvmViewModel(
    private val userRepo: UserRepo
) : ScopeViewModel<MvvmState>(
    MvvmState.Initial
) {
    private var counter = 0

    fun fetchData() = launchWithIO {
        if (state is MvvmState.LoadingData) return@launchWithIO
        state = MvvmState.LoadingData

        val listUsers = userRepo.getListUsers()
        state = MvvmState.ShowData(listUsers)
    }

    fun addCounter() {
        state = MvvmState.Clicked(counter++)
    }

    fun removeCounter() {
        state = MvvmState.Clicked(counter--)
    }
}