package com.github.kyrillosgait.gifnic.ui.main.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.kyrillosgait.gifnic.R
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.remote.DataMode
import com.github.kyrillosgait.gifnic.ui.common.loadWebp
import com.github.kyrillosgait.gifnic.ui.common.loadWebpWithPlaceholder
import com.github.kyrillosgait.gifnic.ui.common.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gif.*

class GifsAdapter(
    private val clickListener: (Gif) -> Unit
) : PagedListAdapter<Gif, GifsAdapter.ViewHolder>(GifDiffCallback()) {

    private val set = ConstraintSet()

    var dataMode: DataMode = DataMode.WIFI

    // region Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_gif, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gif = getItem(position) ?: return
        holder.bind(gif, clickListener)
    }

    // endregion

    // region ViewHolder

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(gif: Gif, clickListener: (Gif) -> Unit) {
            val image = when (dataMode) {
                // Use the fixed width GIF if running on WiFi
                DataMode.WIFI -> gif.images.fixedWidth
                // Use the downsampled fixed width GIF if running on mobile data
                DataMode.MOBILE_DATA -> gif.images.fixedWidthDownsampled
            }

            // Calculate the image ratio
            val ratio = "${image.width}:${image.height}"

            // Set it to the layout to avoid surprises in the staggered grid layout manager
            set.apply {
                clone(gifConstraintLayout)
                setDimensionRatio(gifImageView.id, ratio)
                applyTo(gifConstraintLayout)
            }

            when (dataMode) {
                DataMode.WIFI -> gifImageView.loadWebpWithPlaceholder(
                    url = image.webp,
                    placeholder = gif.images.fixedWidthStill.url
                )
                DataMode.MOBILE_DATA -> gifImageView.loadWebp(url = image.webp)
            }

            gifImageView.onClick { clickListener(gif) }
        }
    }

    // endregion

    // region DiffUtil.ItemCallback

    private class GifDiffCallback : DiffUtil.ItemCallback<Gif>() {
        override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem == newItem
        }
    }

    // endregion
}