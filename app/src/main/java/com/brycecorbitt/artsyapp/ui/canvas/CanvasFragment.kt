package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.view.*
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.brycecorbitt.artsyapp.LocationService
import com.brycecorbitt.artsyapp.R
import com.brycecorbitt.artsyapp.api.Post
import com.brycecorbitt.artsyapp.api.ResponseHandler
import java.io.ByteArrayOutputStream


class CanvasFragment : Fragment() {

//    private val canvasViewModel: CanvasViewModel by lazy {
//    ViewModelProviders.of(this).get(CanvasViewModel::class.java)
//    }
    private lateinit var canvasViewModel : CanvasViewModel
    private lateinit var apiCall: ResponseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    //Views
    private lateinit var canvasView: CanvasView
    private lateinit var colorBar: SeekBar
    private lateinit var lightBar: SeekBar
    private lateinit var clearButton: Button
    private lateinit var undoButton: Button
    private lateinit var submitButton: Button
    private lateinit var colorIndicator: TextView
    private lateinit var lightnessIndicator: TextView
    private lateinit var compositeIndicator: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        canvasViewModel = CanvasViewModel()

        apiCall = ResponseHandler(activity?.applicationContext)
        lateinit var submitResponse : LiveData<Post>

        val root = inflater.inflate(R.layout.fragment_canvas, container, false)

        canvasView = root.findViewById(R.id.canvasView)
        colorBar = root.findViewById(R.id.seekBar_color)
        lightBar = root.findViewById(R.id.seekBar_blackwhite)
        clearButton = root.findViewById(R.id.button_clear)
        undoButton = root.findViewById(R.id.button_undo)
        submitButton = root.findViewById(R.id.button_finish)
        colorIndicator = root.findViewById(R.id.colorTV)
        lightnessIndicator = root.findViewById(R.id.blackwhiteTV)
        compositeIndicator = root.findViewById(R.id.compositeTV)

        // Persist all color and seekerBar progress info
        colorBar.progress = CanvasViewModel.colorBarProgress!!
        colorBar.max = CanvasViewModel.colorBarMax
        lightBar.progress = CanvasViewModel.lightBarProgress!!
        colorIndicator.setBackgroundColor(canvasViewModel.getColor())
        colorBar.thumb.setTint((canvasViewModel.getColorOnly()))
        lightBar.thumb.setTint((canvasViewModel.getWhiteBlack()))
        colorIndicator.setBackgroundColor(canvasViewModel.getColorOnly())
        lightnessIndicator.setBackgroundColor(canvasViewModel.getWhiteBlack())
        compositeIndicator.setBackgroundColor(canvasViewModel.getColor())
        CanvasView.templatePaint = CanvasViewModel.currentPaint!!


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


            val base64String : String = convertBitmap(canvasView.getDrawingCache())


            val latitude : Float = LocationService.current_location?.latitude?.toFloat() as Float
            val longitude : Float = LocationService.current_location?.longitude?.toFloat() as Float
            val response: LiveData<Post> = apiCall.createPost(latitude, longitude, base64String)
            response.observe(
                this.viewLifecycleOwner,
                Observer {
                    var msg = ""
                    if (it != null){
                        msg = getString(R.string.toast_submit_success)
                    } else{
                        msg = getString(R.string.toast_submit_fail)
                    }
                    Toast.makeText(activity?.applicationContext, msg, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigation_account)
                }
            )
        }


        colorBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    CanvasViewModel.colorBarProgress = progress
                    CanvasView.templatePaint.color = canvasViewModel.setPaintHue(seek.progress.toFloat())
                    colorIndicator.setBackgroundColor(canvasViewModel.getColorOnly())
                    seek.thumb.setTint((canvasViewModel.getColorOnly()))
                    compositeIndicator.setBackgroundColor(canvasViewModel.getColor())
                }

                override fun onStartTrackingTouch(seek: SeekBar) {}
                override fun onStopTrackingTouch(seek: SeekBar) {}
            })
        lightBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    CanvasViewModel.lightBarProgress = progress
                    CanvasView.templatePaint.color = canvasViewModel.setPaintVal(progress.toFloat())
                    lightnessIndicator.setBackgroundColor(canvasViewModel.getWhiteBlack())
                    seek.thumb.setTint((canvasViewModel.getWhiteBlack()))
                    compositeIndicator.setBackgroundColor(canvasViewModel.getColor())
                }

                override fun onStartTrackingTouch(seek: SeekBar) {}
                override fun onStopTrackingTouch(seek: SeekBar) { }
            })
        return root

    }

    fun convertBitmap(bm : Bitmap) : String{

        val byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP or Base64.URL_SAFE)
    }

    fun screenshotCanvas(view: View, window: Window?, callback: (Bitmap) -> Unit) {
        window?.let { window ->
            val destBM = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val rectLocations = IntArray(2)
            view.getLocationInWindow(rectLocations)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    PixelCopy.request(
                        window,
                        Rect(
                            rectLocations[0],
                            rectLocations[1],
                            rectLocations[0] + view.width,
                            rectLocations[1] + view.height
                        ), destBM, { copyResult ->
                            if (copyResult == PixelCopy.SUCCESS) {
                                callback(destBM)
                            }
                        },
                        Handler()
                    )
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }



}

