package com.github.kyrillosgait.gifnic.data.remote

import com.github.kyrillosgait.gifnic.data.models.Pagination
import kotlinx.serialization.Serializable

/**
 * Successful response from GIPHY [Api].
 */
@Serializable
data class GiphyResponse<T>(
    override val data: T? = null,
    val pagination: Pagination = Pagination()
) : ApiResponse<T>
