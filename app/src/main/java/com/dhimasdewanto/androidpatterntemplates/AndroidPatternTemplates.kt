package com.dhimasdewanto.androidpatterntemplates

import android.app.Application
import com.dhimasdewanto.androidpatterntemplates.features.logic.repositories.UserRepo
import com.dhimasdewanto.androidpatterntemplates.features.logic.services.JsonPlaceholderService
import com.dhimasdewanto.androidpatterntemplates.features.logic.use_cases.GetListUsers
import com.dhimasdewanto.androidpatterntemplates.features.ui.mvi.MviViewModelFactory
import com.dhimasdewanto.androidpatterntemplates.features.ui.mvvm.MvvmViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

@Suppress("unused")
class AndroidPatternTemplates : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@AndroidPatternTemplates))

        bind() from singleton { JsonPlaceholderService() }
        bind() from singleton { UserRepo(instance()) }
        bind() from singleton { GetListUsers(instance()) }
        bind() from provider { MviViewModelFactory(instance()) }
        bind() from provider { MvvmViewModelFactory(instance()) }
    }
}