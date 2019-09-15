package com.github.kyrillosgait.gifnic.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Contains information relating to the number of total results available,
 * as well as the number of results fetched and their relative positions.
 */
@Serializable @Parcelize
data class Pagination(
    val offset: Int = 0,
    val count: Int = 0,
    @SerialName("total_count") val totalCount: Int = 0
) : Parcelable