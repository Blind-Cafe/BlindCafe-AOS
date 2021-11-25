package com.abouttime.blindcafe.presentation.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class SwipeRefreshLayoutWithOverscroll(context: Context, attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {
    interface ScrollResolver {
        fun canScrollUp(): Boolean
    }

    private var mScrollResolver: ScrollResolver? = null
    fun setScrollResolver(scrollResolver: ScrollResolver) {
        mScrollResolver = scrollResolver
    }



    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return child.canScrollVertically(0) && super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

    override fun canChildScrollUp(): Boolean {
        mScrollResolver?.let { sr ->
            return sr.canScrollUp()
        } ?: kotlin.run {
            return super.canChildScrollUp()
        }


    }


}