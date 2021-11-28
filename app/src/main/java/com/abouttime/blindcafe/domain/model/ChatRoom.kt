package com.abouttime.blindcafe.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatRoom(
    val matchingId: Int?,
    val nickname: String?,
    val profileImage: String?,
    val drink: String?,
    val startTime: String?,
    val interest: String?
): Parcelable