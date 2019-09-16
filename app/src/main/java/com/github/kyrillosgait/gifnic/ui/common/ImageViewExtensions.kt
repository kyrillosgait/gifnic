package com.github.kyrillosgait.gifnic.ui.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Loads a GIF image from a url into the [ImageView].
 */
fun ImageView.loadGif(
    url: String,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC
) {
    Glide.with(context)
        .asGif()
        .load(url)
        .diskCacheStrategy(diskCacheStrategy)
        .into(this)
}

/**
 * Loads a WEBP image from a url into the [ImageView].
 */
fun ImageView.loadWebp(
    url: String,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC
) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(diskCacheStrategy)
        .into(this)
}

fun ImageView.loadAnimatedGifWithPlaceholder(
    animatedUrl: String,
    staticUrl: String
) {
    Glide.with(context)
        .asGif()
        .thumbnail(Glide.with(context).asGif().load(staticUrl))
        .load(animatedUrl)
        .into(this)
}