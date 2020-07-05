package com.dhimasdewanto.androidpatterntemplates.core.mvi

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class ScopeViewModel<S: IState, I: IIntent>(
    val intentChannel: Channel<I>,
    private val initialState: S
) : ViewModel() {
    private val _viewModelState = MutableLiveData<S>().apply {
        value = initialState
    }
    val viewModelState : LiveData<S>
        get() = _viewModelState

    val state: S
        get() = viewModelState.value!!

    abstract val handleIntent: suspend (intent: I) -> Unit

    init {
        initIntentHandler()
    }

    private fun initIntentHandler() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                handleIntentWithIO(it)
            }
        }
    }

    private fun handleIntentWithIO(intent: I) {
        viewModelScope.launch(Dispatchers.IO) {
            handleIntent(intent)
        }
    }

    protected fun updateState(handler: suspend (state: S) -> S) {
        viewModelScope.launch(Dispatchers.Main) {
            _viewModelState.value = handler(viewModelState.value!!)
        }
    }

    fun observe(owner: LifecycleOwner, observerMethod: (state: S) -> Unit) {
        viewModelState.observe(owner, Observer {
            observerMethod(it)
        })
    }
}