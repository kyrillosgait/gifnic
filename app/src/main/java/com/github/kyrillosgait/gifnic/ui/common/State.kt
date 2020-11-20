package com.github.kyrillosgait.gifnic.ui.common

/**
 * Represents the various states of [StatefulLiveData].
 */
sealed class State<T, E> {

    /**
     * The resource is loading.
     */
    class Loading<T, E> internal constructor() : State<T, E>()

    /**
     * The resource loaded with [data].
     */
    class Success<T, E> internal constructor(val data: T) : State<T, E>()

    /**
     * The resource is empty.
     */
    class Empty<T, E> internal constructor() : State<T, E>()

    /**
     * The resource failed to load.
     */
    class Error<T, E> internal constructor(val errors: List<E>) : State<T, E>() {
        constructor(error: E) : this(listOf(error))
    }
}
