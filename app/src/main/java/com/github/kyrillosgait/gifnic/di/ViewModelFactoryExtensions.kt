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
 * Returns a [Lazy] delegate to access activity's ViewModel, provided by Dagger 2.
 *
 * ```
 * class MyActivity : Activity() {
 *     val viewModel by viewModel { myActivityViewModel }
 * }
 * ```
 */
inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    crossinline provider: KnownViewModels.() -> Provider<T>
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            provider((application as ComponentProvider).component.knownViewModels).get() as T
    }
}

/**
 * Returns a [Lazy] delegate to access the ViewModel from a fragment, provided by Dagger 2.
 *
 * ```
 * class MyFragment : Fragment() {
 *     val viewModel by viewModel { myFragmentViewModel }
 * }
 * ```
 */
inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline provider: KnownViewModels.() -> Provider<T>
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            provider((activity?.application as ComponentProvider).component.knownViewModels).get() as T
    }
}

/**
 * Returns a [Lazy] delegate to access an activity's ViewModel from a fragment, provided by Dagger 2.
 *
 * ```
 * class MyFragment : Fragment() {
 *     val activityViewModel by activityViewModel { myActivityViewModel }
 * }
 * ```
 */
inline fun <reified T : ViewModel> Fragment.activityViewModel(
    crossinline provider: KnownViewModels.() -> Provider<T>
) = activityViewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            provider((activity?.application as ComponentProvider).component.knownViewModels).get() as T
    }
}