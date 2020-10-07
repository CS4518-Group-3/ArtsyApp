package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        canvasViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }
}