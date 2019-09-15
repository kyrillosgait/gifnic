package com.github.kyrillosgait.gifnic.ui.main.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.kyrillosgait.gifnic.R
import com.github.kyrillosgait.gifnic.di.viewModel
import com.github.kyrillosgait.gifnic.ui.common.State
import com.github.kyrillosgait.gifnic.ui.common.loadWebp
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel by viewModel { detailViewModel }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initToolbar()
        initImage()
    }

    private fun initToolbar() {
        detailToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun initImage() {
        detailToolbar.title = safeArgs.gif.title
        detailAnimatedImageView.loadWebp(url = safeArgs.gif.images.fixedWidth.webp)

        viewModel.randomGif.observe(this, Observer {
            when (it) {
                is State.Loading -> Unit
                is State.Empty -> Unit
                is State.Success -> {
                    detailToolbar.title = it.data.title
                    detailAnimatedImageView.loadWebp(url = it.data.images.fixedWidth.webp)
                }
                is State.Error -> Unit
            }
        })
    }

}