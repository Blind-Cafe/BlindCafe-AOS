package com.abouttime.blindcafe.common.ext

import android.net.Uri
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.abouttime.blindcafe.R
import com.bumptech.glide.Glide

internal fun ImageView.setChatImage(uri: Uri?) {
    val progressDrawable = CircularProgressDrawable(this.context)
    progressDrawable.strokeWidth = 15f
    progressDrawable.centerRadius = 30f
    progressDrawable.start()

    Glide.with(this.context)
        .load(uri)
        .placeholder(progressDrawable)
        .into(this)

    this.setWrapContent()
    this.setWidthByDisplaySize(0.67f)
}

internal fun ImageView.setImageUrl(url: String) {
    val progressDrawable = CircularProgressDrawable(this.context)
    progressDrawable.strokeWidth = 15f
    progressDrawable.centerRadius = 30f
    progressDrawable.start()

    Glide.with(this.context)
        .load(url)
        .placeholder(progressDrawable)
        .into(this)
}


internal fun ImageView.setImageUrlByCircle(url: String) {
    val progressDrawable = CircularProgressDrawable(this.context)
    progressDrawable.strokeWidth = 15f
    progressDrawable.centerRadius = 30f
    progressDrawable.backgroundColor = this.context.resources.getColor(R.color.main, null)
    progressDrawable.start()

    Glide.with(this.context)
        .load(url)
        .placeholder(progressDrawable)
        .circleCrop()
        .into(this)
}