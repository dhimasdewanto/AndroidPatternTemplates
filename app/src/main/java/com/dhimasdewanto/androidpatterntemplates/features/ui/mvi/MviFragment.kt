package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimasdewanto.androidpatterntemplates.R
import com.dhimasdewanto.androidpatterntemplates.core.recycler_adapter.SimpleRecyclerAdapter
import com.dhimasdewanto.androidpatterntemplates.core.mvi.ScopeFragment
import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel
import kotlinx.android.synthetic.main.fragment_mvi.*
import kotlinx.android.synthetic.main.item_user.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MviFragment : ScopeFragment<MviState, MviIntent>(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory by instance<MviViewModelFactory>()

    private lateinit var viewModel: MviViewModel
    private lateinit var recyclerAdapter: SimpleRecyclerAdapter<UserModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mvi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MviViewModel::class.java)
        setViewModelState(viewModel.viewModelState)
        setIntentChannel(viewModel.intentChannel)

        "MVI".setAppBarTitle()
        initRecyclerView()

        setOnClickButtons()
    }

    override fun handleState(state: MviState) {
        text_main.visibility = View.INVISIBLE
        loading_circular.visibility = View.INVISIBLE
        recycler_view_users.visibility = View.INVISIBLE

        when (state) {
            is MviState.Initial -> {
                text_main.text = getString(R.string.state_initial)
                text_main.visibility = View.VISIBLE
            }
            is MviState.Clicked -> {
                text_main.text = "${state.count}"
                text_main.visibility = View.VISIBLE
            }
            is MviState.LoadingData -> {
                loading_circular.visibility = View.VISIBLE
            }
            is MviState.ShowData -> {
                recyclerAdapter.submitList(state.listUsers)
                recycler_view_users.visibility = View.VISIBLE
            }
            is MviState.ShowError -> {
                text_main.text = state.message
                text_main.visibility = View.VISIBLE
            }
        }
    }

    private fun setOnClickButtons() {
        button_add.setOnClickListener {
            sendIntent(MviIntent.Add)
        }

        button_remove.setOnClickListener {
            sendIntent(MviIntent.Remove)
        }

        button_fetch.setOnClickListener {
            sendIntent(MviIntent.FetchData)
        }

        button_to_mvvm.setOnClickListener {
            findNavController().navigate(R.id.action_mviFragment_to_mvvmFragment)
        }

        button_to_camerax.setOnClickListener {
            findNavController().navigate(R.id.action_mviFragment_to_cameraXFragment)
        }
    }

    private fun initRecyclerView() {
        recyclerAdapter = SimpleRecyclerAdapter(
            layoutItem = R.layout.item_user,
            areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            bindItem = { item, itemView ->
                itemView.text_name.text = item.name
                itemView.text_email.text = item.email
            }
        )

        recycler_view_users.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

    private fun String.setAppBarTitle() {
        activity?.title = this
    }
}