package com.github.kyrillosgait.gifnic.ui.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kyrillosgait.gifnic.data.GifRepository
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.Answer
import com.github.kyrillosgait.gifnic.ui.common.StatefulLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A [ViewModel] to be used with [DetailFragment].
 */
class DetailViewModel(private val repository: GifRepository) : ViewModel() {

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
        delay(timeMillis = 10_000)

        with(receiver = _randomGif) {
            // Make sure there is an active observer before loading a new GIF
            if (hasActiveObservers()) {
                when (val answer = repository.getRandom()) {
                    is Answer.Success -> postSuccess(answer.value)
                    is Answer.Error -> postError(answer.error)
                }
            }
        }

        loadRandomGif()
    }
}
