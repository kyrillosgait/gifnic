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
import com.github.kyrillosgait.gifnic.ui.common.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gif.*

class GifsAdapter(
    private val clickListener: (Gif) -> Unit
) : PagedListAdapter<Gif, GifsAdapter.ViewHolder>(GifDiffCallback()) {

    var dataMode: DataMode = DataMode.WIFI

    private val set = ConstraintSet()

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
                DataMode.WIFI -> gif.images.fixedWidth
                DataMode.MOBILE_DATA -> gif.images.fixedWidthDownsampled
            }

            val ratio = "${image.width}:${image.height}"

            set.apply {
                clone(gifConstraintLayout)
                setDimensionRatio(gifImageView.id, ratio)
                applyTo(gifConstraintLayout)
            }

            gifImageView.loadWebp(url = image.webp)
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