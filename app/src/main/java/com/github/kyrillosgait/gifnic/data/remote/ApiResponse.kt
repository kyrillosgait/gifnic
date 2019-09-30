package com.github.kyrillosgait.gifnic.data.remote

/**
 * Generic api response that should be implemented by a successful response object
 * like [GiphyResponse].
 */
interface ApiResponse<T> {
    val data: T?
}