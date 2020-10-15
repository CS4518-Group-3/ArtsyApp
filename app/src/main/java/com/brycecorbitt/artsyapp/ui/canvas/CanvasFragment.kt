package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.view.*
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.brycecorbitt.artsyapp.R
import java.io.ByteArrayOutputStream


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
        colorBar.progress = canvasViewModel.colorBarProgress
        lightBar.progress = canvasViewModel.lightBarProgress
        val clearButton: Button =root.findViewById(R.id.button4)
        val undoButton: Button =root.findViewById(R.id.button5)
        val submitButton: Button =root.findViewById(R.id.button)
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
        submitButton.setOnClickListener {
            //var bitmap : Bitmap = Bitmap.
            //canvasView.lastCanvas
            //activity(canvasView, this., )
            var encoded : String = "TODO: Post Base64 string"
            //screenshotCanvas(canvasView, activity?.window, ))
                //Toast.makeText(activity?.applicationContext, encoded, Toast.LENGTH_SHORT).show()

            Toast.makeText(activity?.applicationContext, encoded, Toast.LENGTH_SHORT).show()

        }
        colorBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    CanvasView.templatePaint.color =
                        canvasViewModel.setPaintHue(seek.progress.toFloat())
                    colorIndicator.setBackgroundColor(canvasViewModel.getColor())
                    seek.thumb.setTint((canvasViewModel.getColorOnly()))
                }

                override fun onStartTrackingTouch(seek: SeekBar) {}
                override fun onStopTrackingTouch(seek: SeekBar) {

                }
            })
        lightBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    CanvasView.templatePaint.color =
                        canvasViewModel.setPaintVal(seek.progress.toFloat() / 100f)
                    colorIndicator.setBackgroundColor(canvasViewModel.getColor())
                    seek.thumb.setTint((canvasViewModel.getWhiteBlack()))
                }

                override fun onStartTrackingTouch(seek: SeekBar) {}
                override fun onStopTrackingTouch(seek: SeekBar) {

                }
            })
//        canvasViewModel.text.observe(viewLifecycleOwner, Observer {
//            //textView.text = it
//        })
        return root

    }

    fun convertBitmap(bm : Bitmap) : String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun screenshotCanvas(view: View, window: Window?, callback: (Bitmap) -> Unit) {
        window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    PixelCopy.request(
                        window,
                        Rect(
                            locationOfViewInWindow[0],
                            locationOfViewInWindow[1],
                            locationOfViewInWindow[0] + view.width,
                            locationOfViewInWindow[1] + view.height
                        ), bitmap, { copyResult ->
                            if (copyResult == PixelCopy.SUCCESS) {
                                callback(bitmap)
                            } else {

                            }
                            // possible to handle other result codes ...
                        },
                        Handler()
                    )
                }
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }



}

