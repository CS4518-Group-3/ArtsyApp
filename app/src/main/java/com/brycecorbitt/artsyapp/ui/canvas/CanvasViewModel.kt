package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Paint
import android.graphics.Path
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CanvasViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is canvas Fragment"
//    }
    var currentPaint: Paint = Paint()

}