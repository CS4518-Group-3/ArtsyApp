package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
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
        val lightBar: SeekBar = root.findViewById(R.id.seekBar3)
        val clearButton: Button =root.findViewById(R.id.button4)
        val undoButton: Button =root.findViewById(R.id.button5)
        //seekBar.max = 255
        clearButton.setOnClickListener {

            CanvasView.paintList.clear()
            CanvasView.pathList.clear()
            canvasView.invalidate()
        }
        undoButton.setOnClickListener {

            CanvasView.paintList.removeAt(CanvasView.paintList.size - 1)
            CanvasView.pathList.removeAt(CanvasView.pathList.size - 1)
            canvasView.invalidate()
        }
        lightBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                //Toast.makeText(activity,"Hue is: " + (seek.progress / 100 * 360)+ ".", Toast.LENGTH_SHORT).show()
                val floatarray : FloatArray = FloatArray(3)
                // Hue
                floatarray[0] = CanvasView.templatePaint.color
                // Saturation
                floatarray[1] = 1f
                // Value
                floatarray[2] = 1f
                if (seek.progress == 0){
                    floatarray[0] = 1f
                }
                else if (seek.progress == 100){
                    floatarray[1] = 0f
                }

                CanvasView.templatePaint.color = Color.HSVToColor(floatarray)
            }
        })
        colorBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                //Toast.makeText(activity,"Hue is: " + (seek.progress / 100 * 360)+ ".", Toast.LENGTH_SHORT).show()
                val floatarray : FloatArray = FloatArray(3)
                // Hue
                floatarray[0] = (seek.progress* 3.6).toFloat()
                // Saturation
                floatarray[1] = 1f
                // Value
                floatarray[2] = 1f

                CanvasView.templatePaint.color = Color.HSVToColor(floatarray)
            }
        })
//        canvasViewModel.text.observe(viewLifecycleOwner, Observer {
//            //textView.text = it
//        })
        return root
    }
}

