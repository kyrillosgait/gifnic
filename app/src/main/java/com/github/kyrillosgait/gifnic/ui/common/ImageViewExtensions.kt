package com.github.kyrillosgait.gifnic.ui.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Loads a WEBP image from a url into the [ImageView].
 *
 * @param url the url of the image to load.
 * @param diskCacheStrategy the caching strategy.
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

/**
 * Loads a WEBP image from a url into the [ImageView], with a placeholder.
 *
 * @param url the url of the image to load.
 * @param placeholder the url of the placeholder image to load before the main image.
 * @param diskCacheStrategy the caching strategy.
 */
fun ImageView.loadWebpWithPlaceholder(
    url: String,
    placeholder: String,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC
) {
    Glide.with(context)
        .load(url)
        .thumbnail(Glide.with(context).asDrawable().load(placeholder))
        .diskCacheStrategy(diskCacheStrategy)
        .into(this)
}