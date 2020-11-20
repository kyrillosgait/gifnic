package com.github.kyrillosgait.gifnic.ui.common

import androidx.lifecycle.LiveData

/**
 * A loadable resource.
 */
class StatefulLiveData<T, E> : LiveData<State<T, E>>() {

    /**
     * The internal state of the resource.
     */
    private var state: State<T, E> = State.Empty()

    /**
     * Returns this [StatefulLiveData] as [LiveData] to expose this [StatefulLiveData] as a raw [LiveData].
     */
    fun asLiveData(): LiveData<State<T, E>> = this

    /**
     * Posts a [State.Loading].
     */
    fun postLoading() {
        postValue(State.Loading())
    }

    /**
     * Posts a [State.Success] with [data].
     */
    fun postSuccess(data: T) {
        postValue(State.Success(data))
    }

    /**
     * Posts a [State.Empty].
     */
    fun postEmpty() {
        postValue(State.Empty())
    }

    /**
     * Posts a [State.Error] with a [E].
     *
     * @param error the error
     */
    fun postError(error: E) {
        postValue(State.Error(error))
    }

    /**
     * Posts a [State.Error] with a [E].
     *
     * @param errors the errors
     */
    fun postErrors(errors: List<E>) {
        postValue(State.Error(errors))
    }
}
