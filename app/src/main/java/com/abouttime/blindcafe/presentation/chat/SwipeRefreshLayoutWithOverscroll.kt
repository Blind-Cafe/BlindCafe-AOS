package com.abouttime.blindcafe.presentation.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshLayoutWithOverscroll(context: Context, attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {
    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return child.canScrollVertically(0) && super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

}