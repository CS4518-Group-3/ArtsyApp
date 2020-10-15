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



    val hsv : FloatArray = FloatArray(3)

    fun setPaintHue(hue: Float ) : Int {
        hsv[0] = hue
        return Color.HSVToColor(hsv)
    }
    fun setPaintVal(lightness: Float): Int{
        hsv[2] = lightness
        return Color.HSVToColor(hsv)
    }
    fun getColor() : Int{
        return Color.HSVToColor(hsv)
    }
    var currentPaint: Paint = Paint()
    var barProgress = 0;

//    val colorSeeklistener : SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
//        override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
//            barProgress = seek.progress
//            setPaintHue(progress.toFloat())
//        }
//        override fun onStartTrackingTouch(seek: SeekBar) { }
//        override fun onStopTrackingTouch(seek: SeekBar) { }
//    }
//    val lightnessSeeklistener : SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
//        override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
//            barProgress = seek.progress
//            setPaintVal(progress.toFloat())
//        }
//        override fun onStartTrackingTouch(seek: SeekBar) { }
//        override fun onStopTrackingTouch(seek: SeekBar) { }
//    }
    init {
        currentPaint.style = Paint.Style.STROKE
        currentPaint.strokeWidth = 10F
        hsv[0] = 0f
        hsv[1] = 1f
        hsv[2] = 1f
    }
}