package com.dhimasdewanto.androidpatterntemplates.core.mvvm

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class ScopeFragment<State> : Fragment() {
    protected fun setViewModelState(state : LiveData<State>) {
        state.observe(this, Observer {
            handleState(it)
        })
    }

    abstract fun handleState(state: State)
}