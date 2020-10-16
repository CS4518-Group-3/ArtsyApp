package com.brycecorbitt.artsyapp.ui.canvas

import android.graphics.Color
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class CanvasViewModelTest {

    lateinit var canvasViewModel: CanvasViewModel

    @Before
    fun setUp() {
        canvasViewModel = CanvasViewModel()
    }

    @Test
    fun testCanvasViewModel() {
        assertEquals(canvasViewModel.getColor(), Color.HSVToColor(CanvasViewModel.hsv))
    }
}