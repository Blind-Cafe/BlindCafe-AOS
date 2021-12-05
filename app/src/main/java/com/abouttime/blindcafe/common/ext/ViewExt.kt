package com.abouttime.blindcafe.common.ext


import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.core.view.updateLayoutParams

fun View.setMarginTop(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    topMargin = value
}

fun View.setMarginBottom(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    bottomMargin = value
}

fun View.setMarginLeft(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    leftMargin = value
}

fun View.setMarginRight(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    rightMargin = value
}

internal fun View.setHeightByDisplaySize(ratio: Float) {
    val windowHeight = this.context.resources.displayMetrics.heightPixels
    this.layoutParams.height = (windowHeight * (ratio)).toInt()
}

internal fun View.setWidthByDisplaySize(ratio: Float) {
    val windowHeight = this.context.resources.displayMetrics.widthPixels
    this.layoutParams.width = (windowHeight * (ratio)).toInt()
}

internal fun View.setWrapContent() {
    this.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
    this.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
}