package com.dhimasdewanto.androidpatterntemplates.features.ui.mvi

import com.dhimasdewanto.androidpatterntemplates.core.mvi.IIntent

sealed class MainIntent : IIntent {
    object Add : MainIntent()

    object Remove : MainIntent()

    object FetchData : MainIntent()
}