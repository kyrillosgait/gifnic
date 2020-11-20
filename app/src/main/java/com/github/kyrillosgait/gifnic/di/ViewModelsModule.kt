package com.github.kyrillosgait.gifnic.di

import com.github.kyrillosgait.gifnic.ui.main.detail.DetailViewModel
import com.github.kyrillosgait.gifnic.ui.main.trending.TrendingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { TrendingViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
