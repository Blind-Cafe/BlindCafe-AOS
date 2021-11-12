package com.abouttime.blindcafe.presentation.main.home.coffee

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {

        view.apply {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.coffee_page_margin)
            val pagerWidth = resources.getDimensionPixelOffset(R.dimen.coffee_page_width)
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth
            view.translationX = position * -offsetPx

            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            scaleX = scaleFactor
            scaleY = scaleFactor

        }
    }

    companion object {
        const val MIN_SCALE = 0.9f
        const val MIN_ALPHA = 0.5f
    }
}