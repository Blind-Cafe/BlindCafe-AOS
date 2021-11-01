package com.abouttime.blindcafe.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RvGridDecoration(private val horizontalSpace: Int, private val verticalSpace: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        outRect.set(horizontalSpace, verticalSpace, horizontalSpace, verticalSpace)

    }
}