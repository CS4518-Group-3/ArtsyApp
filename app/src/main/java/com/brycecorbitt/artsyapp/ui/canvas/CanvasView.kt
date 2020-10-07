package com.brycecorbitt.artsyapp.ui.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet) {
    val path: Path = Path()
    val paint: Paint = Paint()
    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var returnBool = super.onTouchEvent(event)
        if (event != null){
            val x = event.x
            val y = event.y
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {path.moveTo(x, y); returnBool = true}
                MotionEvent.ACTION_MOVE -> {path.lineTo(x, y); returnBool = true}
                else -> returnBool = false
            }

        }
        invalidate()
        return returnBool
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
        //path.reset()
    }
}