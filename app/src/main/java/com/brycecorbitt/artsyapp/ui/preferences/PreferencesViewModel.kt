package com.brycecorbitt.artsyapp.ui.preferences

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException

class PreferencesViewModel private constructor(context: Context) : ViewModel() {
    private var unit: String = "mi"
    private var radius: Float = 5F
    private var isLocation: Boolean = true
    private var lat: Float = 0.0F //user's location later
    private var long: Float = 0.0F //user's location later
    private var locationButtonText: String = "Enter a Location:"

    val isCurrentUnitMiles: String
        get() = unit
    val isCurrentSortLocation: Boolean
        get() = isLocation
    val currentRadius: Float
        get() = radius
    val currentLat: Float
        get() = lat
    val currentLong: Float
        get() = long
    val currentLocationButtonText: String
        get() = locationButtonText
    fun setCurrentUnit(u: String) { unit = u }
    fun setIsCurrentSortLocation(trueOrNah: Boolean) { isLocation = trueOrNah }
    fun setRadius(rad: Float) { radius = rad }
    fun setLat(lt: Float) { lat = lt}
    fun setLong(lg: Float) { long = lg }
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