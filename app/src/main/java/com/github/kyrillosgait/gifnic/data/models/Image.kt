package com.github.kyrillosgait.gifnic.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

/**
 * Contains [Gif]'s url, width, height and size information. In a few types of [Images], it also
 * contains webp url.
 */
@Serializable @Parcelize
data class Image(
    val url: String = "",
    val width: String = "",
    val height: String = "",
    val size: String = "",
    val webp: String = ""
) : Parcelable