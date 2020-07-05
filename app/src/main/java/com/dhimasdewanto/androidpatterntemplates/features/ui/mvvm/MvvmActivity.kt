package com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.androidpatterntemplates.R
import com.dhimasdewanto.androidpatterntemplates.core.mvvm.ScopeActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mvvm.*
import kotlinx.android.synthetic.main.activity_mvvm.button_add
import kotlinx.android.synthetic.main.activity_mvvm.button_fetch
import kotlinx.android.synthetic.main.activity_mvvm.button_remove
import kotlinx.android.synthetic.main.activity_mvvm.loading_circular
import kotlinx.android.synthetic.main.activity_mvvm.text_main
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MvvmActivity : ScopeActivity<MvvmState>(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory by instance<MvvmViewModelFactory>()

    private lateinit var viewModel: MvvmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MvvmViewModel::class.java)
        setViewModelState(viewModel.viewModelState)

        "MVVM".setAppBarTitle()

        setOnClickButtons()
    }

    override fun handleState(state: MvvmState) {
        text_main.visibility = View.GONE
        loading_circular.visibility = View.GONE

        when (state) {
            MvvmState.Initial -> {
                text_main.text = getString(R.string.state_initial)
                text_main.visibility = View.VISIBLE
            }
            MvvmState.LoadingData -> {
                loading_circular.visibility = View.VISIBLE
            }
            is MvvmState.Clicked -> {
                text_main.text = "${state.count}"
                text_main.visibility = View.VISIBLE
            }
            is MvvmState.ShowData -> {
                text_main.text = state.listUsers[0].email
                text_main.visibility = View.VISIBLE
            }
        }
    }

    private fun setOnClickButtons() {
        button_add.setOnClickListener {
            viewModel.addCounter()
        }

        button_remove.setOnClickListener {
            viewModel.removeCounter()
        }

        button_fetch.setOnClickListener {
            viewModel.fetchData()
        }

        button_to_mvi.setOnClickListener {
            finish()
        }
    }

    private fun String.setAppBarTitle() {
        actionBar?.title = this
        supportActionBar?.title = this
    }
}