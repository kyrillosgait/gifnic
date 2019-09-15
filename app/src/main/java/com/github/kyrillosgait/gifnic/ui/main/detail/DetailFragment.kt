package com.github.kyrillosgait.gifnic.ui.main.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.kyrillosgait.gifnic.R
import com.github.kyrillosgait.gifnic.ui.common.loadWebp
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initToolbar()
        initImage()
    }


    private fun initToolbar() {
        detailToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun initImage() {
        detailAnimatedImageView.loadWebp(url = safeArgs.gif.images.fixedHeight.webp)
    }

}