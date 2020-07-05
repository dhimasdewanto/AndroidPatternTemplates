package com.dhimasdewanto.androidpatterntemplates.core.mvi

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

abstract class ScopeActivity<State : IState, Intent : IIntent> : AppCompatActivity() {
    private lateinit var intentChannel: Channel<Intent>

    protected fun setViewModelState(state: LiveData<State>) {
        state.observe(this, Observer {
            handleState(it)
        })
    }

    protected fun setIntentChannel(ic: Channel<Intent>) {
        intentChannel = ic
    }

    protected fun sendIntent(intent: Intent) {
        lifecycleScope.launch {
            intentChannel.send(intent)
        }
    }

    abstract fun handleState(state: State)
}