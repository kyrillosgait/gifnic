package com.github.kyrillosgait.gifnic.data.remote

import retrofit2.Response
import timber.log.Timber

const val GENERIC_ERROR = "Something went wrong."

/**
 * Makes the api call, parses the response and returns the result as an [Answer].
 *
 * @param apiRequest the api call to be executed.
 *
 * @return an [Answer] that may be a:
 * - [Answer.Success] with the response data, or
 * - [Answer.Error] with an error message.
 */
inline fun <R : ApiResponse<T>, T> requestAnswer(
    apiRequest: () -> Response<R>
): Answer<T, String> {
    try {
        val response = apiRequest()

        when (response.isSuccessful) {
            true -> {
                val body = response.body()?.data ?: return Answer.Error(GENERIC_ERROR)
                return Answer.Success(body)
            }
            false -> return Answer.Error(GENERIC_ERROR)
        }
    } catch (e: Exception) {
        Timber.e(e)
        return Answer.Error(GENERIC_ERROR)
    }
}

/**
 * Makes the api call, parses the response and returns the response result as an [Answer]. In contrary
 * to [requestAnswer] it returns the whole [GiphyResponse] object, so it can be used to gain important
 * information for requesting the next page with Paging library from Architecture Components.
 *
 * @param apiRequest the api call to be executed.
 *
 * @return an [Answer] that may be a:
 * - [Answer.Success] with the response data, pagination, and meta, or
 * - [Answer.Error] with an error message.
 */
inline fun <R : ApiResponse<T>, T> requestAnswerPaginated(
    apiRequest: () -> Response<R>
): Answer<R, String> {
    try {
        val response = apiRequest()

        when (response.isSuccessful) {
            true -> {
                val body = response.body() ?: return Answer.Error(GENERIC_ERROR)
                return Answer.Success(body)
            }
            false -> return Answer.Error(GENERIC_ERROR)
        }
    } catch (e: Exception) {
        Timber.e(e)
        return Answer.Error(GENERIC_ERROR)
    }
}
