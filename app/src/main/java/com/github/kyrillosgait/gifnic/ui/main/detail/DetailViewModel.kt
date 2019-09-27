package com.github.kyrillosgait.gifnic.ui.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.Answer
import com.github.kyrillosgait.gifnic.ui.common.StatefulLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A [ViewModel] to be used with [DetailFragment].
 */
class DetailViewModel @Inject constructor(private val repository: GifRepository) : ViewModel() {

    private val _randomGif = StatefulLiveData<Gif, String>()

    /** A random GIF. */
    val randomGif = _randomGif.asLiveData()

    init {
        /**
         * Start a coroutine within the ViewModel's scope, so it gets cancelled automatically
         * when the ViewModel is no longer needed.
         */
        viewModelScope.launch {
            loadRandomGif()
        }
    }

    /**
     * Fetches a new random GIF every 10 seconds.
     */
    private suspend fun loadRandomGif() {
        delay(10_000)

        // Make sure there is an active observer before loading a new GIF
        if (_randomGif.hasActiveObservers()) {
            _randomGif.postLoading()
            when (val answer = repository.getRandom()) {
                is Answer.Success -> _randomGif.postSuccess(answer.value)
                is Answer.Error -> _randomGif.postError(answer.error)
            }
        }

        loadRandomGif()
    }
}