package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.mvi.ScopeViewModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.repositories.UserRepo
import kotlinx.coroutines.channels.Channel

class MainViewModel(
    private val userRepo: UserRepo
) : ScopeViewModel<MainState, MainIntent>(
    intentChannel = Channel(Channel.UNLIMITED),
    initialState = MainState.Initial
) {
    private var counter = 0

    override val handleIntent: suspend (intent: MainIntent) -> Unit
        get() = {
            when(it) {
                MainIntent.Add -> addCounter()
                MainIntent.Remove -> removeCounter()
                MainIntent.FetchData -> fetchData()
            }
        }

    private suspend fun fetchData() {
        if (state is MainState.LoadingData) return

        updateState {
            MainState.LoadingData
        }

        val listUsers = userRepo.getListUsers()
        updateState {
            MainState.ShowData(listUsers)
        }
    }

    private fun addCounter() {
        updateState {
            MainState.Clicked(counter++)
        }
    }

    private fun removeCounter() {
        updateState {
            MainState.Clicked(counter--)
        }
    }
}