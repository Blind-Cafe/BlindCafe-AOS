package com.abouttime.blindcafe.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Profile(
    val age: Int?,
    val sex: String?,
    val interests: List<String>?,
    val nickname: String?,
    val profileImage: String?,
    val location: String?,
    val userId: Int?
): Parcelable
