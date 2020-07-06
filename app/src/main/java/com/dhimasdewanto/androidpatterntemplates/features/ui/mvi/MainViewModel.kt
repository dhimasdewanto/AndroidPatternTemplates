package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.mvi.ScopeViewModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.repositories.UserRepo
import kotlinx.coroutines.channels.Channel

class MainViewModel(
    private val userRepo: UserRepo
) : ScopeViewModel<MainState, MainIntent>(
    initialState = MainState.Initial
) {
    private var counter = 0

    override suspend fun handleIntent(intent: MainIntent) {
        when(intent) {
            MainIntent.Add -> addCounter()
            MainIntent.Remove -> removeCounter()
            MainIntent.FetchData -> fetchData()
        }
    }

    private suspend fun fetchData() {
        if (state is MainState.LoadingData) return
        state = MainState.LoadingData

        val listUsers = userRepo.getListUsers()
        state = MainState.ShowData(listUsers)
    }

    private fun addCounter() {
        state = MainState.Clicked(counter++)
    }

    private fun removeCounter() {
        state = MainState.Clicked(counter--)
    }
}