package com.github.kyrillosgait.gifnic.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif

class GifsDataFactory(
    private val repository: GifRepository
) : DataSource.Factory<Int, Gif>() {

    val mutableLiveData: MutableLiveData<GifsDataSource>

    private var gifsDataSource: GifsDataSource? = null

    init {
        this.mutableLiveData = MutableLiveData<GifsDataSource>()
    }

    override fun create(): DataSource<Int, Gif> {
        gifsDataSource = GifsDataSource(repository)
        mutableLiveData.postValue(gifsDataSource)
        return gifsDataSource as GifsDataSource
    }
}