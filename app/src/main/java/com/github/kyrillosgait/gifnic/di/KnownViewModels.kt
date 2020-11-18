package com.github.kyrillosgait.gifnic.di

import com.github.kyrillosgait.gifnic.ui.main.detail.DetailViewModel
import com.github.kyrillosgait.gifnic.ui.main.trending.TrendingViewModel
import javax.inject.Inject
import javax.inject.Provider

/**
 * Provides the ViewModels.
 */
class KnownViewModels @Inject constructor(
    val trendingViewModel: Provider<TrendingViewModel>,
    val detailViewModel: Provider<DetailViewModel>
)