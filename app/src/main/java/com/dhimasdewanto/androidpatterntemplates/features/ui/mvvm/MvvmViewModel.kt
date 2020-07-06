package com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm

import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Err
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Ok
import com.dhimasdewanto.androidpatterntemplates.core.mvvm.ScopeViewModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.failures.Failures
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

        val resUsers = userRepo.getListUsers()
        state = when(resUsers) {
            is Ok -> MvvmState.ShowData(resUsers.value)
            is Err -> {
                when(resUsers.failure) {
                    Failures.SomeFailure -> MvvmState.ShowError("Some Failure")
                    Failures.ExampleFailure -> MvvmState.ShowError("Example Failure")
                }
            }
        }
    }

    fun addCounter() {
        state = MvvmState.Clicked(counter++)
    }

    fun removeCounter() {
        state = MvvmState.Clicked(counter--)
    }
}