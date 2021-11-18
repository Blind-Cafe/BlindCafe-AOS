package com.abouttime.blindcafe.presentation.chat.recorder

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountUpTextView(context: Context, attrs: AttributeSet) :AppCompatTextView(context, attrs) {
    private var startTimeStamp: Long = 0L
    private val countUpAction: Runnable = object : Runnable {
        override fun run() {
            val currentTimeStamp = SystemClock.elapsedRealtime()
            val countTimeSeconds = ((currentTimeStamp - startTimeStamp)/1000L).toInt()
            updateCountTime(countTimeSeconds)
            handler?.postDelayed(this, 1000)
        }
    }

    fun startCountUp() {
        startTimeStamp = SystemClock.elapsedRealtime()
        handler?.post(countUpAction)
    }
    fun stopCountUp() {
        handler?.removeCallbacks(countUpAction)
    }
    fun clearCountTime() {
        updateCountTime(0)
    }
    private fun updateCountTime(countTimeSeconds: Int) {
        val minutes = countTimeSeconds / 60
        val seconds = countTimeSeconds % 60
        // TextView 를 상속 받았기 때문에 이렇게 속성을 넣어줄 수 있다.
        text = "%02d:%02d".format(minutes, seconds)
    }
}