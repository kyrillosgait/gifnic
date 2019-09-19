package com.github.kyrillosgait.gifnic.ui.main.trending

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.ConnectivityManagerCompat.isActiveNetworkMetered
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.kyrillosgait.gifnic.R
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.DataMode
import com.github.kyrillosgait.gifnic.di.activityViewModel
import com.github.kyrillosgait.gifnic.ui.common.State
import com.github.kyrillosgait.gifnic.ui.common.gone
import com.github.kyrillosgait.gifnic.ui.common.invisible
import com.github.kyrillosgait.gifnic.ui.common.onClick
import com.github.kyrillosgait.gifnic.ui.common.visible
import kotlinx.android.synthetic.main.fragment_trending.*

/**
 * A simple [Fragment] subclass.
 */
class TrendingFragment : Fragment(R.layout.fragment_trending) {

    // region Properties

    private val activityViewModel by activityViewModel { mainViewModel }

    private val connectivityManager by lazy {
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (gifsAdapter.dataMode != dataMode(connectivityManager)) {
                    gifsAdapter.dataMode = dataMode(connectivityManager)
                }
            }
        }
    }

    private val dataMode: (ConnectivityManager) -> DataMode = { cm ->
        when (isActiveNetworkMetered(cm)) {
            true -> DataMode.MOBILE_DATA
            false -> DataMode.WIFI
        }
    }

    private lateinit var gifsAdapter: GifsAdapter
    private lateinit var gifsLayoutManager: StaggeredGridLayoutManager

    // endregion

    // region Lifecycle

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val toggleDarkMode: () -> Unit = {
            AppCompatDelegate.setDefaultNightMode(
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }
            ).also { activity?.recreate() }
        }

        trendingNightModeToggleIcon.onClick { toggleDarkMode() }

        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }
    }

    override fun onStop() {
        super.onStop()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    // endregion

    // region Init

    private fun initRecyclerView() {
        val onGifClicked: (Gif) -> Unit = {
            findNavController().navigate(
                TrendingFragmentDirections.actionTrendingFragmentToDetailFragment(gif = it)
            )
        }

        gifsLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        gifsAdapter = GifsAdapter { onGifClicked(it) }.apply {
            dataMode = dataMode(connectivityManager)
        }

        gifsRecyclerView.apply {
            adapter = gifsAdapter
            layoutManager = gifsLayoutManager
        }

        activityViewModel.gifs.observe {
            when (it) {
                is State.Loading -> {
                    trendingProgressBar.visible()
                    errorIcon.gone()
                }
                is State.Success -> {
                    gifsAdapter.submitList(it.data)
                    trendingProgressBar.gone()
                    errorIcon.gone()
                }
                is State.Empty -> Unit
                is State.Error -> {
                    errorIcon.visible()
                    trendingProgressBar.gone()
                }
            }
        }

        val makeToolbarInvisibleOnScroll: (View, RecyclerView, StaggeredGridLayoutManager) -> Unit =
            { toolbar, recyclerView, layoutManager ->
                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val firstVisibleItem =
                            layoutManager.findFirstCompletelyVisibleItemPositions(null).first()

                        if (firstVisibleItem == 0) toolbar.visible() else toolbar.invisible()
                    }
                })
            }

        makeToolbarInvisibleOnScroll(trendingToolbar, gifsRecyclerView, gifsLayoutManager)
    }

    // endregion

    private fun <T> LiveData<T>.observe(consumer: (T) -> Unit) {
        observe(this@TrendingFragment, Observer { consumer(it) })
    }
}