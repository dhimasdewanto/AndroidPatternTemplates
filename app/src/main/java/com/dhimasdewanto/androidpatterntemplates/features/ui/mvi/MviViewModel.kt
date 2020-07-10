package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Err
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Ok
import com.dhimasdewanto.androidpatterntemplates.core.mvi.ScopeViewModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.failures.Failures
import com.dhimasdewanto.androidpatterntemplates.features.logic.use_cases.GetListUsers
import com.dhimasdewanto.androidpatterntemplates.features.logic.use_cases.GetListUsersParams

class MviViewModel(
    private val getListUsers: GetListUsers
) : ScopeViewModel<MviState, MviIntent>(
    initialState = MviState.Initial
) {
    private var counter = 0

    override suspend fun handleIntent(intent: MviIntent) {
        when (intent) {
            MviIntent.Add -> addCounter()
            MviIntent.Remove -> removeCounter()
            MviIntent.FetchData -> fetchData()
        }
    }

    private suspend fun fetchData() {
        if (state is MviState.LoadingData) return
        state = MviState.LoadingData

        val resUsers = getListUsers.call(
            GetListUsersParams(5)
        )
        state = when (resUsers) {
            is Ok -> MviState.ShowData(resUsers.value)
            is Err -> {
                when (resUsers.failure) {
                    Failures.SomeFailure -> MviState.ShowError("Some Failure")
                    Failures.ExampleFailure -> MviState.ShowError("Example Failure")
                }
            }
        }
    }

    private fun addCounter() {
        state = MviState.Clicked(counter++)
    }

    private fun removeCounter() {
        state = MviState.Clicked(counter--)
    }
}