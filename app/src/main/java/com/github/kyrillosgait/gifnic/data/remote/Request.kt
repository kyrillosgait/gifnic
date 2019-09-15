package com.github.kyrillosgait.gifnic.data.remote

import kotlinx.serialization.UnstableDefault
import retrofit2.Response
import timber.log.Timber

/**
 * Makes the api call, parses the response and returns the result as an [Answer].
 *
 * @param apiRequest the api call to be executed.
 *
 * @return an [Answer] that may be a:
 * - [Answer.Success] with the response data, or
 * - [Answer.Error] with an error message from the api.
 */
@UnstableDefault
inline fun <T> requestAnswer(
    apiRequest: () -> Response<GiphyResponse<T>>
): Answer<T, String> {
    try {
        val response = apiRequest()

        when (response.isSuccessful) {
            true -> {
                // If body is null, return Answer.Error
                val body = response.body()?.data ?: return Answer.Error("Something went wrong")

                return Answer.Success(body)
            }
            false -> return Answer.Error("Something went wrong")
        }
    } catch (e: Exception) {
        Timber.e(e)
        return Answer.Error("Something went wrong")
    }
}

@UnstableDefault
inline fun <T> requestAnswerPaginated(
    apiRequest: () -> Response<GiphyResponse<T>>
): Answer<GiphyResponse<T>, String> {
    try {
        val response = apiRequest()

        when (response.isSuccessful) {
            true -> return Answer.Success(response.body()!!)
            false -> return Answer.Error("Something went wrong")
        }
    } catch (e: Exception) {
        Timber.e(e)
        return Answer.Error("Something went wrong")
    }
}