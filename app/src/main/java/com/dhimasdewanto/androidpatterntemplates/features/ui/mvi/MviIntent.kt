package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.mvi.IIntent

sealed class MviIntent : IIntent {
    object Add : MviIntent()

    object Remove : MviIntent()

    object FetchData : MviIntent()
}