package com.github.kyrillosgait.gifnic.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Contains basic information regarding the response and its status.
 */
@Serializable @Parcelize
data class Meta(
    val msg: String = "",
    val status: Int = 0,
    @SerialName("response_id") val responseId: String = ""
) : Parcelable