package com.abouttime.blindcafe.common.ext

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.util.Preconditions
import com.bumptech.glide.util.Util
import java.nio.ByteBuffer
import java.security.MessageDigest


class RoundedCornersLineOverDrawTransFormation @JvmOverloads constructor(
    roundingRadius: Int,
    lineWidth: Float,
    lineColor: Int = Color.parseColor("#0F000000"),
) :
    BitmapTransformation() {
    private val roundingRadius: Int
    private val lineWidth: Float
    private val lineColor: Int
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int,
    ): Bitmap {
        val result = TransformationUtils.roundedCorners(pool, toTransform, roundingRadius)
        val borderPaint = Paint()
        borderPaint.isAntiAlias = true
        borderPaint.color = lineColor
        val rect = RectF(0f, 0f, outWidth.toFloat(), outHeight.toFloat())
        val lineRect = RectF(lineWidth, lineWidth, outWidth - lineWidth, outHeight - lineWidth)
        val canvas = Canvas(result)
        val path = Path()
        path.addRoundRect(lineRect,
            roundingRadius.toFloat(),
            roundingRadius.toFloat(),
            Path.Direction.CCW)
        canvas.clipPath(path, Region.Op.DIFFERENCE)
        canvas.drawRoundRect(rect, roundingRadius.toFloat(), roundingRadius.toFloat(), borderPaint)
        return result
    }

    override fun equals(o: Any?): Boolean {
        if (o is RoundedCornersLineOverDrawTransFormation) {
            val other = o
            return lineWidth == other.lineWidth && roundingRadius == other.roundingRadius
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(ID.hashCode(), Util.hashCode(lineWidth + roundingRadius))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
        val radiusData = ByteBuffer.allocate(4).putFloat(lineWidth + roundingRadius).array()
        messageDigest.update(radiusData)
    }

    companion object {
        private const val ID = "com_charlezz_pickle_RoundedCornersLineOverDrawTransFormation"
        private val ID_BYTES = ID.toByteArray(CHARSET)
    }

    init {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.")
        Preconditions.checkArgument(lineWidth > 0, "lineWidth must be greater than 0.")
        this.roundingRadius = roundingRadius
        this.lineWidth = lineWidth
        this.lineColor = lineColor
    }
}