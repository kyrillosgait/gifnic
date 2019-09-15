@file:Suppress("UNCHECKED_CAST")

package com.github.kyrillosgait.gifnic.di

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

/**
 * The following extension functions enable us to lazy initialize the viewModel in an activity or
 * in a fragment in one line, using by viewModels() Kotlin property delegate
 * provided by activity-ktx/fragment-ktx.
 */

inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    crossinline provider: KnownViewModels.() -> Provider<T>
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            provider((application as ComponentProvider).component.knownViewModels).get() as T
    }
}

inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline provider: KnownViewModels.() -> Provider<T>
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            provider((activity?.application as ComponentProvider).component.knownViewModels).get() as T
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(
    crossinline provider: KnownViewModels.() -> Provider<T>
) = activityViewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            provider((activity?.application as ComponentProvider).component.knownViewModels).get() as T
    }
}