package com.github.kyrillosgait.gifnic.ui.main.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.PagedList
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.GifsPagedSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

/**
 * A [ViewModel] to be used with [TrendingFragment].
 */
class TrendingViewModel(repository: GifRepository) : ViewModel() {

    private val _gifs = MutableLiveData<PagedList<Gif>>()

    /** An endless list of trending GIFs. */
    val gifs: LiveData<PagedList<Gif>> = _gifs

    init {
        /**
         * Configure how GifPagedList loads content from the data source.
         */
        val pagingConfig = Config(
            pageSize = 20,
            initialLoadSizeHint = 40,
            enablePlaceholders = false
        )

        /**
         * Build the GifPagedList.
         *
         * The upcoming version of Paging library, will natively support Coroutines.
         *
         * [Dispatchers] will be used instead of [Executor]s, as well as the ability to build a paged
         * list with a specific coroutine scope. This allows the paged list to suspend or cancel
         * the loading operations when this coroutine scope is terminated.
         */
        val gifsPagedList = PagedList(
            dataSource = GifsPagedSource(repository),
            config = pagingConfig,
            // Use main thread to dispatch paged list updates
            notifyExecutor = Executor { runBlocking(Dispatchers.Main) { it.run() } },
            fetchExecutor = Executor { it.run() }
        )

        _gifs.postValue(gifsPagedList)
    }
}