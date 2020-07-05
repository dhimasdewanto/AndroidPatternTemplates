package com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.androidpatterntemplates.features.logic.repositories.UserRepo

@Suppress("UNCHECKED_CAST")
class MvvmViewModelFactory(
    private val userRepo: UserRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) : T {
        return MvvmViewModel(userRepo) as T
    }
}