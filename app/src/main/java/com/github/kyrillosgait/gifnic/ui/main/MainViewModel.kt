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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault
import javax.inject.Inject

@UnstableDefault
class MainViewModel @Inject constructor(private val repository: GifRepository) : ViewModel() {

    private val _gifs = StatefulLiveData<PagedList<Gif>, String>()

    /** A list of trending gifs */
    val gifs = _gifs.asLiveData()

    init {
        _gifs.postLoading()

        val myPagingConfig = Config(
            pageSize = 20,
            initialLoadSizeHint = 40,
            enablePlaceholders = false
        )

        val gifsPagedList = PagedList.Builder(GifsDataSource(repository), myPagingConfig)
            .setFetchExecutor { it.run() }
            .setNotifyExecutor { runBlocking(Dispatchers.Main) { it.run() } }
            .build()

        viewModelScope.launch {
            delay(1000)
            _gifs.postSuccess(gifsPagedList)
        }
    }

/*    private suspend fun loadGifs() {
        _gifs.postLoading()
        when (val answer = repository.getTrending()) {
            is Answer.Success -> _gifs.postSuccess(answer.value)
            is Answer.Error -> _gifs.postError("Generic error")
        }
    }*/
}