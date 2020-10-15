package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.brycecorbitt.artsyapp.R

class CanvasFragment : Fragment() {

    private lateinit var canvasViewModel: CanvasViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        canvasViewModel =
            ViewModelProviders.of(this).get(CanvasViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_canvas, container, false)
        //val textView: TextView = root.findViewById(R.id.text_canvas)
        //val base: CanvasView = root.findViewById(R.id.canvasView)
        //base.setContent
        val canvasView: CanvasView = root.findViewById(R.id.canvasView)//CanvasView(this.context)
        val colorBar: SeekBar = root.findViewById(R.id.seekBar)
        colorBar.max = 320
        val lightBar: SeekBar = root.findViewById(R.id.seekBar3)
        lightBar.progress = 100
        val clearButton: Button =root.findViewById(R.id.button4)
        val undoButton: Button =root.findViewById(R.id.button5)
        val colorIndicator: TextView = root.findViewById(R.id.textView)
        colorIndicator.setBackgroundColor(canvasViewModel.getColor())
        //seekBar.max = 255
        clearButton.setOnClickListener {
            CanvasView.paintList.clear()
            CanvasView.pathList.clear()
            canvasView.invalidate()
        }
        undoButton.setOnClickListener {
            if (CanvasView.paintList.size > 0){
                CanvasView.paintList.removeAt(CanvasView.paintList.size - 1)
                CanvasView.pathList.removeAt(CanvasView.pathList.size - 1)
                canvasView.invalidate()
            }
        }
        colorBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                CanvasView.templatePaint.color = canvasViewModel.setPaintHue(seek.progress.toFloat())
                colorIndicator.setBackgroundColor(canvasViewModel.getColor())
                seek.thumb.setTint((canvasViewModel.getColorOnly()))
            }
            override fun onStartTrackingTouch(seek: SeekBar) { }
            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })
        lightBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    CanvasView.templatePaint.color = canvasViewModel.setPaintVal(seek.progress.toFloat() / 100f)
                    colorIndicator.setBackgroundColor(canvasViewModel.getColor())
                    seek.thumb.setTint((canvasViewModel.getWhiteBlack()))
                }
                override fun onStartTrackingTouch(seek: SeekBar) { }
                override fun onStopTrackingTouch(seek: SeekBar) {

                }
            })
//        canvasViewModel.text.observe(viewLifecycleOwner, Observer {
//            //textView.text = it
//        })
        return root
    }
}

