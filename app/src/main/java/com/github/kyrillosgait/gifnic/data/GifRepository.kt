package com.github.kyrillosgait.gifnic.data

import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.Answer
import com.github.kyrillosgait.gifnic.data.remote.GiphyResponse

/**
 * A repository that manages fetching GIF images from the API.
 */
interface GifRepository {

    /**
     * Fetches the current trending GIFs.
     *
     * @return either:
     * - an [Answer.Success] containing a list of GIFs, or
     * - an [Answer.Error] containing an error message.
     */
    suspend fun getTrendingPaginated(offset: Int): Answer<GiphyResponse<List<Gif>>, String>

    /**
     * Fetches a random GIF.
     *
     * @return either:
     * - an [Answer.Success] containing a random GIF, or
     * - an [Answer.Error] containing an error message.
     */
    suspend fun getRandom(): Answer<Gif, String>
}