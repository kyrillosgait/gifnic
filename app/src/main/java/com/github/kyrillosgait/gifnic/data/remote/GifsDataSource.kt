package com.github.kyrillosgait.gifnic.data.remote

import androidx.paging.PageKeyedDataSource
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20

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
                        response.value.pagination.offset - PAGE_SIZE,
                        response.value.pagination.offset + PAGE_SIZE
                    )
                }
                is Answer.Error -> Unit
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
                is Answer.Error -> Unit
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Gif>) {
//        GlobalScope.launch {
//            when (val response = repository.getTrendingPaginated(offset = params.key - 40)) {
//                is Answer.Success -> {
//                    callback.onResult(
//                        response.value.data.orEmpty(),
//                        response.value.pagination?.offset
//                    )
//                }
//                is Answer.Error -> Unit
//            }
//        }
    }
}