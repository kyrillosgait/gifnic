package com.github.kyrillosgait.gifnic.di

import com.github.kyrillosgait.gifnic.ui.main.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

/**
 * Provides the ViewModels.
 */
class KnownViewModels @Inject constructor(
    val mainViewModel: Provider<MainViewModel>
)