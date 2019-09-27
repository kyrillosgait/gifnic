package com.github.kyrillosgait.gifnic.di

import com.github.kyrillosgait.gifnic.ui.main.TrendingViewModel
import com.github.kyrillosgait.gifnic.ui.main.detail.DetailViewModel
import kotlinx.serialization.UnstableDefault
import javax.inject.Inject
import javax.inject.Provider

/**
 * Provides the ViewModels.
 */
@UnstableDefault
class KnownViewModels @Inject constructor(
    val trendingViewModel: Provider<TrendingViewModel>,
    val detailViewModel: Provider<DetailViewModel>
)