package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CanvasViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is canvas Fragment"
//    }





    fun setPaintHue(hue: Float ) : Int {
        hsv?.set(0, hue)
        return Color.HSVToColor(hsv)
    }
    fun setPaintVal(lightness: Float): Int{
        hsv?.set(2, lightness / 100f)
        return Color.HSVToColor(hsv)
    }
    fun getColor() : Int{
        return Color.HSVToColor(hsv)
    }
    fun getColorOnly() : Int{
        val hsvCopy = hsv?.clone()
        hsvCopy?.set(2, 1f)
        return Color.HSVToColor(hsvCopy)
        return Color.HSVToColor(hsv)
    }
    fun getWhiteBlack() : Int{
        val hsvCopy = hsv?.clone()
        hsvCopy?.set(1, 0f)
        return Color.HSVToColor(hsvCopy)
    }


    init {

        if (colorBarProgress == null) {

            colorBarProgress = 0

        }
        if (lightBarProgress == null) {
            lightBarProgress = 0
        }
        if (hsv == null) {
            hsv = FloatArray(3)
            hsv!![0] = 0f
            hsv!![1] = 1f
            hsv!![2] = 1f
        }
        if (currentPaint == null) {
            currentPaint = Paint()
            currentPaint!!.style = Paint.Style.STROKE
            currentPaint!!.strokeWidth = 10F
            currentPaint!!.color = getColor()
        }

    }

    companion object {
        var currentPaint: Paint? = null
        var colorBarProgress : Int? = null
        var colorBarMax = 340
        var lightBarProgress : Int? = null
        var hsv : FloatArray? = null
    }

}