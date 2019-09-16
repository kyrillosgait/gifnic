package com.github.kyrillosgait.gifnic.data.remote

import com.github.kyrillosgait.gifnic.data.models.Gif
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
;

// region Constants
const val BASE_URl = "https://api.giphy.com/v1/"

private const val GIFS_TRENDING = "gifs/trending"
private const val GIFS_RANDOM = "gifs/random"
private const val API_KEY = "dc6zaTOxFJmzC"
// endregion

/**
 * Interface to define GIPHY api calls.
 */
interface Api {

    /**
     * Returns a list of the most relevant and engaging content each and every day.
     *
     * @param apiKey giphy API key.
     * @param limit the maximum number of objects to return.
     * @param offset specifies the starting position of the results.
     * @param rating filters results by specified rating.
     * @param randomId an ID/proxy for a specific user.
     *
     * @return a list of trending GIFs
     */
    @GET(GIFS_TRENDING)
    suspend fun getTrending(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("limit") limit: String? = "20",
        @Query("offset") offset: String? = null,
        @Query("rating") rating: String? = null,
        @Query("random_id") randomId: String? = null
    ): Response<GiphyResponse<List<Gif>>>

    /**
     * Returns a single random GIF.
     *
     * @param apiKey giphy API key.
     * @param tag filters results by specified tag.
     * @param rating filters results by specified rating.
     * @param randomId an ID/proxy for a specific user.
     *
     * @return a random GIF.
     */
    @GET(GIFS_RANDOM)
    suspend fun getRandom(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("tag") tag: String? = null,
        @Query("rating") rating: String? = null,
        @Query("random_id") randomId: String? = null
    ): Response<GiphyResponse<Gif>>
}