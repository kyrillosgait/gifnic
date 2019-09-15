package com.github.kyrillosgait.gifnic.data.remote

/**
 * Usage of this class should reduce the amount of app crashes by representing errors as returned
 * values rather than thrown exceptions.
 *
 *  [Answer] results in either:
 *  - [Answer.Success] (successful state with value) or,
 *  - [Answer.Error] with an error value.
 */
sealed class Answer<T, E> {

    /**
     * See [Answer].
     *
     * @param T the type of value returned if the answer is successful.
     * @param E the type of error return if the answer is not successful.
     * @param value the value.
     */
    class Success<T, E>(val value: T) : Answer<T, E>()

    /**
     * See [Answer].
     *
     * @param T the type of value returned if the answer is successful.
     * @param E the type of error return if the answer is not successful.
     * @param error the error.
     */
    class Error<T, E>(val error: E) : Answer<T, E>()

}