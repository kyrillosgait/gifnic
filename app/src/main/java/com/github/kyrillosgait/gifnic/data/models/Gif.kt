package com.github.kyrillosgait.gifnic.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

/**
 * Contains a variety of information, such as the [Images],
 * which itself includes the URLS for multiple different GIFS formats and sizes.
 */
@Serializable
@Parcelize
data class Gif(
    val id: String = "",
    val title: String = "",
    val images: Images = Images()
) : Parcelable
