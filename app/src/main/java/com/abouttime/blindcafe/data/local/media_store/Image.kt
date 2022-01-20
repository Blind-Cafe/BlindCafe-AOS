package com.abouttime.blindcafe.data.local.media_store

import android.net.Uri

data class Image(
    val id: Long = 1,
    val bucketId: String ="",
    val dataAdded: Long= 1,
    val fileSize: Long= 1,
    val mimeType: String= "",
    val orientation: Int=1,
    val uri: Uri
)