package com.brycecorbitt.artsyapp.ui.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet) {

    init {
        Companion.templatePaint.style = Paint.Style.STROKE
        Companion.templatePaint.strokeWidth = 10F
        Companion.templatePaint.color = Color.RED
    }
    var lastCanvas : Canvas = Canvas()
    

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var returnBool = super.onTouchEvent(event)
        if (event != null){
            val x = event.x
            val y = event.y
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val path = Path()
                    val paint = Paint(Companion.templatePaint)
                    path.moveTo(x, y)
                    Companion.pathList.add(path)
                    Companion.paintList.add(paint)
                    returnBool = true
                }
                MotionEvent.ACTION_MOVE -> {
                    Companion.pathList[(Companion.pathList.size - 1)].lineTo(x, y)
                    returnBool = true
                }
                else -> returnBool = false
            }

        }
        invalidate()
        return returnBool
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (x in 0 until Companion.pathList.size){
            canvas?.drawPath(Companion.pathList[x], Companion.paintList[x])
        }
        if (canvas != null) {
            lastCanvas = canvas
        }


        //path.reset()
    }

    companion object {
        val templatePaint : Paint = Paint()
        val pathList: ArrayList<Path> = ArrayList()
        val paintList: ArrayList<Paint> = ArrayList()

    }
}