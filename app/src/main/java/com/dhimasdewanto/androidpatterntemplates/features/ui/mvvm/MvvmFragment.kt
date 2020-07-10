package com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhimasdewanto.androidpatterntemplates.R
import com.dhimasdewanto.androidpatterntemplates.core.mvvm.ScopeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_mvvm.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MvvmFragment : ScopeFragment<MvvmState>(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory by instance<MvvmViewModelFactory>()

    private lateinit var viewModel: MvvmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mvvm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MvvmViewModel::class.java)
        setViewModelState(viewModel.viewModelState)

        "MVVM".setAppBarTitle()

        setOnClickButtons()
    }

    override fun handleState(state: MvvmState) {
        text_main.visibility = View.INVISIBLE
        loading_circular.visibility = View.INVISIBLE

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
            is MvvmState.ShowError -> {
                text_main.text = state.message
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
            findNavController().popBackStack()
        }
    }

    private fun String.setAppBarTitle() {
        activity?.title = this
    }
}