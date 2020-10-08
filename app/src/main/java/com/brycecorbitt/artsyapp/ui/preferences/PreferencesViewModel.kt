package com.brycecorbitt.artsyapp.ui.preferences

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException

class PreferencesViewModel private constructor(context: Context) : ViewModel() {
    private var isMiles: Boolean = true
    private var radius: Float = 5F
    private var isLocation: Boolean = true
    private var lat: Double = 0.0 //user's location later
    private var long: Double = 0.0 //user's location later
    private var locationButtonText: String = "Enter a Location:"

    val isCurrentUnitMiles: Boolean
        get() = isMiles
    val isCurrentSortLocation: Boolean
        get() = isLocation
    val currentRadius: Float
        get() = radius
    val currentLat: Double
        get() = lat
    val currentLong: Double
        get() = long
    val currentLocationButtonText: String
        get() = locationButtonText
    fun setIsCurrentUnitMiles(trueOrNah: Boolean) { isMiles = trueOrNah }
    fun setIsCurrentSortLocation(trueOrNah: Boolean) { isLocation = trueOrNah }
    fun setRadius(rad: Float) { radius = rad }
    fun setLat(lt: Double) { lat = lt}
    fun setLong(lg: Double) { long = lg }
    fun setLocationButtonText(s: String) { locationButtonText = s }

    companion object {
        private var INSTANCE: PreferencesViewModel? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PreferencesViewModel(context)
            }
        }

        fun get(): PreferencesViewModel {
            return INSTANCE
                ?: throw IllegalStateException("PreferencesViewModel must be initialized")
        }
    }
}