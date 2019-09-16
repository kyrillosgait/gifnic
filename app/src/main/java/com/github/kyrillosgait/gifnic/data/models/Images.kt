package com.github.kyrillosgait.gifnic.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Contains alternate copies of the GIF in a variety of resolutions and formats.
 */
@Serializable @Parcelize
data class Images(
    @SerialName("fixed_height_still") val fixedHeightStill: Image = Image(),
    @SerialName("fixed_height") val fixedHeight: Image = Image(),
    @SerialName("fixed_height_downsampled") val fixedHeightDownsampled: Image = Image(),
    @SerialName("fixed_width_still") val fixedWidthStill: Image = Image(),
    @SerialName("fixed_width") val fixedWidth: Image = Image(),
    @SerialName("fixed_width_downsampled") val fixedWidthDownsampled: Image = Image()
) : Parcelable