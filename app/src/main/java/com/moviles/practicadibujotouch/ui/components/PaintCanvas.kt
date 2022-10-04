package com.moviles.practicadibujotouch.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class PaintCanvas(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mY: Float = 0f
    private var mX: Float = 0f
    private val pathList: ArrayList<Path> = ArrayList()
    private var path: Path = Path()
    private var objPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 20f
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        pathList.forEach {
            canvas?.drawPath(it, objPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x
        val y = event?.y
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
            else -> return false
        }

        return true
    }

    private fun touchUp() {
        path.lineTo(mX, mY)
    }

    private fun touchMove(x: Float?, y: Float?) {
        if (x == null || y == null) return
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= 4 || dy >= 4) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchStart(x: Float?, y: Float?) {
        if (x == null || y == null) {
            return
        }
        pathList.add(path)
        path.moveTo(x, y)
        mX = x
        mY = y
    }
}