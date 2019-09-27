package com.github.kyrillosgait.gifnic.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.PagedList
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.GifsDataSource
import com.github.kyrillosgait.gifnic.ui.common.StatefulLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * A [ViewModel] to be used with [TrendingFragment].
 */
@UnstableDefault
class TrendingViewModel @Inject constructor(repository: GifRepository) : ViewModel() {

    private val _gifs = StatefulLiveData<PagedList<Gif>, String>()

    /** An endless list of trending GIFs. */
    val gifs = _gifs.asLiveData()

    init {
        _gifs.postLoading()

        val myPagingConfig = Config(
            pageSize = 20,
            initialLoadSizeHint = 40,
            enablePlaceholders = false
        )

        val gifsPagedList = PagedList.Builder(GifsDataSource(repository), myPagingConfig)
            .setNotifyExecutor { runBlocking(Dispatchers.Main) { it.run() } }
            .setFetchExecutor { it.run() }
            .build()

        viewModelScope.launch {
            _gifs.postSuccess(gifsPagedList)
        }
    }
}