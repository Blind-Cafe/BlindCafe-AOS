package com.abouttime.blindcafe.presentation.main.home.coffee

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {

        view.apply {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.coffee_page_margin) // dimen 파일 안에 크기를 정의해두었다.
            val pagerWidth = resources.getDimensionPixelOffset(R.dimen.coffee_page_width) // dimen 파일이 없으면 생성해야함
            val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
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