package com.github.kyrillosgait.gifnic.data.remote

import androidx.paging.PagingSource
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import kotlinx.coroutines.delay

private const val PAGE_SIZE = 20
private const val RETRY_IN_MS = 10_000L

/**
 * Incremental data loader for page-keyed content, where requests return keys for next/previous
 * pages.
 *
 * In the case of GIPHY [Api], responses don't contain page numbers but the offset of the (first)
 * item returned. It's pretty easy to calculate the offset which is required for the next page,
 * as it is the offset that was returned from the current api call, plus [PAGE_SIZE].
 *
 * That way, paging library is requesting new pages as the user scrolls, providing the user
 * a smooth scrolling experience.
 *
 * Note: It's not yet Coroutines friendly, but it seems like coroutines support is being added soon:
 * https://android.googlesource.com/platform/frameworks/support/+log/refs/heads/androidx-master-dev/paging/common/src/main/kotlin/androidx/paging
 */
class GifsPagedSource(private val repository: GifRepository) : PagingSource<Int, Gif>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        val offset = params.key ?: 0

        return when (val response = repository.getTrendingPaginated(offset = offset)) {
            is Answer.Success -> LoadResult.Page(
                data = response.value.data.orEmpty(),
                prevKey = null,
                nextKey = response.value.pagination.offset + PAGE_SIZE
            )
            is Answer.Error -> {
                delay(RETRY_IN_MS)
                load(params)
            }
        }
    }
}