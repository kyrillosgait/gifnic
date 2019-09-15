package com.github.kyrillosgait.gifnic.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Image(
    val url: String = "",
    val width: String = "",
    val height: String = "",
    val size: String = "",
    val mp4: String = "",
    val webp: String = "",
    @SerialName("mp4_size") val mp4Size: String = "",
    @SerialName("webp_size") val webp_size: String = ""
) : Parcelable