package com.dhimasdewanto.androidpatterntemplates.core.mvvm

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class ScopeActivity<State> : AppCompatActivity() {
    protected fun setViewModelState(state : LiveData<State>) {
        state.observe(this, Observer {
            handleState(it)
        })
    }

    abstract fun handleState(state: State)
}