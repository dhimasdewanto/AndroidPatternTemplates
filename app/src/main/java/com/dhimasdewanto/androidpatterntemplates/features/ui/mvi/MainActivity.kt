package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.androidpatterntemplates.R
import com.dhimasdewanto.androidpatterntemplates.core.mvi.ScopeActivity
import com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm.MvvmActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : ScopeActivity<MainState, MainIntent>(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory by instance<MainViewModelFactory>()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        setViewModelState(viewModel.viewModelState)
        setIntentChannel(viewModel.intentChannel)

        "MVI".setAppBarTitle()

        setOnClickButtons()
    }

    override fun handleState(state: MainState) {
        text_main.visibility = View.GONE
        loading_circular.visibility = View.GONE

        when (state) {
            is MainState.Initial -> {
                text_main.text = getString(R.string.state_initial)
                text_main.visibility = View.VISIBLE
            }
            is MainState.Clicked -> {
                text_main.text = "${state.count}"
                text_main.visibility = View.VISIBLE
            }
            is MainState.LoadingData -> {
                loading_circular.visibility = View.VISIBLE
            }
            is MainState.ShowData -> {
                text_main.text = state.listUsers[0].name
                text_main.visibility = View.VISIBLE
            }
        }
    }

    private fun setOnClickButtons() {
        button_add.setOnClickListener {
            sendIntent(MainIntent.Add)
        }

        button_remove.setOnClickListener {
            sendIntent(MainIntent.Remove)
        }

        button_fetch.setOnClickListener {
            sendIntent(MainIntent.FetchData)
        }

        button_to_mvvm.setOnClickListener {
            val intent = Intent(this, MvvmActivity::class.java)
            startActivity(intent)
        }
    }

    private fun String.setAppBarTitle() {
        actionBar?.title = this
        supportActionBar?.title = this
    }
}