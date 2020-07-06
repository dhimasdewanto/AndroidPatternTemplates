package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Err
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Ok
import com.dhimasdewanto.androidpatterntemplates.core.mvi.ScopeViewModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.failures.Failures
import com.dhimasdewanto.androidpatterntemplates.features.logic.repositories.UserRepo
import com.dhimasdewanto.androidpatterntemplates.features.logic.use_cases.GetListUsers
import com.dhimasdewanto.androidpatterntemplates.features.logic.use_cases.GetListUsersParams

class MainViewModel(
    private val getListUsers: GetListUsers
) : ScopeViewModel<MainState, MainIntent>(
    initialState = MainState.Initial
) {
    private var counter = 0

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.Add -> addCounter()
            MainIntent.Remove -> removeCounter()
            MainIntent.FetchData -> fetchData()
        }
    }

    private suspend fun fetchData() {
        if (state is MainState.LoadingData) return
        state = MainState.LoadingData

        val resUsers = getListUsers.call(
            GetListUsersParams(5)
        )
        state = when (resUsers) {
            is Ok -> MainState.ShowData(resUsers.value)
            is Err -> {
                when (resUsers.failure) {
                    Failures.SomeFailure -> MainState.ShowError("Some Failure")
                    Failures.ExampleFailure -> MainState.ShowError("Example Failure")
                }
            }
        }
    }

    private fun addCounter() {
        state = MainState.Clicked(counter++)
    }

    private fun removeCounter() {
        state = MainState.Clicked(counter--)
    }
}