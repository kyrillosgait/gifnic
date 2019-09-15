package com.github.kyrillosgait.gifnic.ui.main.trending

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.kyrillosgait.gifnic.R
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.di.activityViewModel
import com.github.kyrillosgait.gifnic.ui.common.State
import com.github.kyrillosgait.gifnic.ui.common.gone
import com.github.kyrillosgait.gifnic.ui.common.onClick
import com.github.kyrillosgait.gifnic.ui.common.visible
import kotlinx.android.synthetic.main.fragment_trending.*

/**
 * A simple [Fragment] subclass.
 */
class TrendingFragment : Fragment(R.layout.fragment_trending) {

    // region Properties

    private val activityViewModel by activityViewModel { mainViewModel }

    private lateinit var gifsAdapter: GifsAdapter
    private lateinit var gifsLayoutManager: StaggeredGridLayoutManager

    // endregion

    // region Lifecycle

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        trendingNightModeToggleIcon.onClick {
            AppCompatDelegate.setDefaultNightMode(
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }
            ).also { activity?.recreate() }
        }
    }

    // endregion

    private fun initRecyclerView() {
        gifsLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val onGifClicked: (Gif) -> Unit = {
            findNavController().navigate(
                TrendingFragmentDirections.actionTrendingFragmentToDetailFragment(gif = it)
            )
        }

        val onGifLongClicked: (Gif) -> Unit = {}

        gifsAdapter = GifsAdapter(
            { onGifClicked(it) },
            { onGifLongClicked(it) }
        )

        gifsRecyclerView.apply {
            adapter = gifsAdapter
            layoutManager = gifsLayoutManager
        }

        activityViewModel.gifs.observe {
            when (it) {
                is State.Loading -> {
                    progressBar.visible()
                    errorIcon.gone()
                }
                is State.Success -> {
                    gifsAdapter.submitList(it.data)
                    progressBar.gone()
                    errorIcon.gone()
                }
                is State.Empty -> Unit
                is State.Error -> {
                    errorIcon.visible()
                    progressBar.gone()
                }
            }
        }
    }

    private fun <T> LiveData<T>.observe(consumer: (T) -> Unit) {
        observe(this@TrendingFragment, Observer { consumer(it) })
    }
}