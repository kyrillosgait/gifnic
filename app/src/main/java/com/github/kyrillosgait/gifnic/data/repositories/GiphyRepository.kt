package com.github.kyrillosgait.gifnic.data.repositories

import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.*

class GiphyRepository(private val api: Api) : GifRepository {

    override suspend fun getTrendingPaginated(offset: Int): Answer<GiphyResponse<List<Gif>>, String> =
        requestAnswerPaginated {
            api.getTrending(offset = offset.toString())
        }

    override suspend fun getRandom(): Answer<Gif, String> = requestAnswer {
        api.getRandom()
    }
}