package com.github.kyrillosgait.gifnic.data.remote

import com.github.kyrillosgait.gifnic.data.models.Meta
import com.github.kyrillosgait.gifnic.data.models.Pagination
import kotlinx.serialization.Serializable

/**
 * Successful response from GIPHY API.
 */
@Serializable
data class GiphyResponse<T>(
    val data: T? = null,
    val pagination: Pagination = Pagination(),
    val meta: Meta = Meta()
)