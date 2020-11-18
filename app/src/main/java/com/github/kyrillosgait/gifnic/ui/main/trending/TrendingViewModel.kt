package com.github.kyrillosgait.gifnic.ui.main.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.remote.GifsPagedSource

/**
 * A [ViewModel] to be used with [TrendingFragment].
 */
class TrendingViewModel(repository: GifRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = false
    )

    val gifs = Pager(pagingConfig) { GifsPagedSource(repository) }
        .liveData
        .cachedIn(viewModelScope)
}