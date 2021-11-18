package com.abouttime.blindcafe.presentation.chat.recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.abouttime.blindcafe.R

class SoundVisualizerView(context : Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var onRequestCurrentAmplitude: (() -> Int)? = null

    // ANTI_ALIAS 는 계단화 방지 도트이미지 같이 보이는 거 방지, 더 부드럽게 보여준다는 느낌
    private val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.purple_500)
        strokeWidth = LINE_WIDTH
        strokeCap = Paint.Cap.ROUND
    }
    private var drawingWidth: Int = 0
    private var drawingHeight: Int = 0
    private var drawingAmplitudes: List<Int> = emptyList()
    private var isReplaying = false
    private var replayingPosition: Int = 0



    private val visualizeRepeatAction: Runnable = object : Runnable {
        override fun run() {
            // RecorderActivity 의 bindView 함수에 있는 onRequestCurrentAmplitude 가 호출되게 된다.
            // 그래서 현재 RecorderActivity 의 오디오 레코드의 maxAplitude 값을 가져올 수 있는 것이다.
            if(isReplaying.not()) {
                val currentAmplitude = onRequestCurrentAmplitude?.invoke() ?: 0
                drawingAmplitudes = listOf(currentAmplitude) + drawingAmplitudes
            } else {
                replayingPosition++
            }

            invalidate() // 데이터가 추가되었을 때 뷰를 갱신하기 위해서 이 함수를 호출했다. 이거 안하면 데이터만 쌓이고 뷰 업데이트 안됨

            // Amplitude, Draw 제일 마지막에 들어온 게 가장 왼쪽에 있게 만들어진다.

            handler?.postDelayed(this, ACTION_INTERVAL)


        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 사이즈를 받아온다.
        drawingWidth = w
        drawingHeight = h

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // length, width, space, color 를 정의하겠다.
        canvas ?: return
        // Y, X 축은 2차원 인덱스로 생각하면 된다. Y가 행이고 아래로 갈 수록 커진다. X는 오른쪽으로 갈 수록 커진다.
        // Y 축에 어디에 그릴지
        val centerY = drawingHeight / 2f
        // X 축에 어디에 그릴지
        var offsetX = drawingWidth.toFloat()

        drawingAmplitudes.let { amplitudes ->
                if(isReplaying) {
                    //하나씩 범위를 늘려가며 리스트를 가져오게 된다.
                    amplitudes.takeLast(replayingPosition)
                } else {
                    // 리플레잉이 아니라면 그냥 리스트 그대로 쓰면된다.
                    amplitudes
                }

            }
            .forEach { amplitude ->
            // 최대 진폭 중에 현재 진폭이 얼마나 비율을 차지하는지  계산
            val lineLength = amplitude / MAX_AMPLITUDE * drawingHeight * 0.8f
            // 오른쪽부터 왼쪽으로 그린다.
            offsetX -= LINE_SPACE
            //바깥 영역으로 나갔다는 의미
            if (offsetX < 0) return@forEach

            canvas.drawLine(
                offsetX,
                centerY - lineLength / 2f,
                offsetX,
                centerY + lineLength / 2f,
                amplitudePaint
            )

        }

    }
    fun startVisualizing(isReplaying: Boolean) {
        this.isReplaying = isReplaying
        // 이러면 visualizeRepeatAction 핸들러가 실행되어 20밀리세컨드마다 뷰 업데이트
        handler?.post(visualizeRepeatAction)
    }
    fun stopVisualizing() {
        // 이러면 뷰  업데이트 종료
        handler.removeCallbacks(visualizeRepeatAction)
        replayingPosition = 0
    }
    fun clearVisualization() {
        drawingAmplitudes = emptyList()
        invalidate()
    }
    companion object {
        private const val LINE_WIDTH = 10f
        private const val LINE_SPACE = 15f
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
        private const val ACTION_INTERVAL = 20L
    }
}