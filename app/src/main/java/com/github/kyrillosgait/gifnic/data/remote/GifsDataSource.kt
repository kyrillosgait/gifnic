package com.github.kyrillosgait.gifnic.data.remote

import androidx.paging.PageKeyedDataSource
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20
private const val RETRY_EVERY_MS = 2_000L

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
class GifsDataSource(private val repository: GifRepository) : PageKeyedDataSource<Int, Gif>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Gif>
    ) {
        GlobalScope.launch {
            when (val response = repository.getTrendingPaginated(offset = 0)) {
                is Answer.Success -> {
                    callback.onResult(
                        response.value.data.orEmpty(),
                        response.value.pagination.offset,
                        response.value.pagination.totalCount,
                        null,
                        response.value.pagination.offset + PAGE_SIZE
                    )
                }
                is Answer.Error -> {
                    delay(RETRY_EVERY_MS)
                    loadInitial(params, callback)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Gif>) {
        GlobalScope.launch {
            when (val response = repository.getTrendingPaginated(offset = params.key)) {
                is Answer.Success -> {
                    callback.onResult(
                        response.value.data.orEmpty(),
                        response.value.pagination.offset + PAGE_SIZE
                    )
                }
                is Answer.Error -> {
                    delay(RETRY_EVERY_MS)
                    loadAfter(params, callback)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Gif>) {
        // This doesn't make sense as the offset start from 0 and there are no negative offsets.
    }
}