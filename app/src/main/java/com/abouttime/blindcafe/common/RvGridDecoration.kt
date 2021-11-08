package com.abouttime.blindcafe.common

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RvGridDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        setHeight(view)
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing * 2
            }
            outRect.bottom = spacing * 2

        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing * 2
            }
        }

    }

    fun setHeight(v: View) {
        if (v.layoutParams is ViewGroup.LayoutParams) {
            v.layoutParams.height = v.layoutParams.width
        }
    }
}