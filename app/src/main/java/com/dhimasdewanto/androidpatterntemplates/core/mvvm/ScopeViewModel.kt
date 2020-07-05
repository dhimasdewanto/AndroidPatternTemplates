package com.dhimasdewanto.androidpatterntemplates.core.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ScopeViewModel<State>(
    private val initialState: State
) : ViewModel() {
    private val _viewModelState = MutableLiveData<State>().apply {
        value = initialState
    }
    val viewModelState : LiveData<State>
        get() = _viewModelState

    var state: State
        get() = viewModelState.value!!
        set(value) {
            launchWithMain {
                _viewModelState.value = value
            }
        }

    protected fun launchWithIO(method: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            method()
        }
    }

    private fun launchWithMain(method: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            method()
        }
    }
}