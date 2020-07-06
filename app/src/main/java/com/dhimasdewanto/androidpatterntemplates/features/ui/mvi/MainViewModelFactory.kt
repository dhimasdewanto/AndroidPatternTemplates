package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.androidpatterntemplates.features.logic.use_cases.GetListUsers

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val getListUsers: GetListUsers
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) : T {
        return MainViewModel(getListUsers) as T
    }
}