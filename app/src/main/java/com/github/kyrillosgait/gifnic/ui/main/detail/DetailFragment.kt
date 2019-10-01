package com.github.kyrillosgait.gifnic.ui.main.detail

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.kyrillosgait.gifnic.R
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.di.viewModel
import com.github.kyrillosgait.gifnic.ui.common.State
import com.github.kyrillosgait.gifnic.ui.common.loadWebp
import com.github.kyrillosgait.gifnic.ui.common.showToast
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A [Fragment] that shows the detail view of one of the trending GIFs.
 *
 * A new, random GIF is shown every 10 seconds.
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()
    private val viewModel by viewModel { detailViewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val getGifTitle: (Gif) -> String = { gif ->
            when {
                gif.title.isNotBlank() -> gif.title
                else -> "untitled GIF"
            }
        }

        detailToolbar.title = getGifTitle(safeArgs.gif)
        detailImageView.loadWebp(url = safeArgs.gif.images.fixedWidth.webp)

        viewModel.randomGif.observe(this, Observer {
            when (it) {
                is State.Loading -> Unit
                is State.Empty -> Unit // Doesn't apply here
                is State.Success -> {
                    detailToolbar.title = getGifTitle(it.data)
                    detailImageView.loadWebp(url = it.data.images.fixedWidth.webp)
                }
                is State.Error -> {
                    showToast(getString(R.string.detail_error), Toast.LENGTH_LONG)
                }
            }
        })
    }
}