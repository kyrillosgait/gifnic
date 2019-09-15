package com.github.kyrillosgait.gifnic.data.repositories

import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.Answer
import com.github.kyrillosgait.gifnic.data.remote.Api
import com.github.kyrillosgait.gifnic.data.remote.GiphyResponse
import com.github.kyrillosgait.gifnic.data.remote.requestAnswer
import com.github.kyrillosgait.gifnic.data.remote.requestAnswerPaginated
import kotlinx.serialization.UnstableDefault
import javax.inject.Inject

@UnstableDefault
class GiphyRepository @Inject constructor(private val api: Api) : GifRepository {

    override suspend fun getTrending(): Answer<List<Gif>, String> = requestAnswer {
        api.getTrending()
    }

    override suspend fun getTrendingPaginated(offset: Int): Answer<GiphyResponse<List<Gif>>, String> =
        requestAnswerPaginated {
            api.getTrending(offset = offset.toString())
        }

    override suspend fun getRandom(): Answer<Gif, String> = requestAnswer {
        api.getRandom()
    }
}