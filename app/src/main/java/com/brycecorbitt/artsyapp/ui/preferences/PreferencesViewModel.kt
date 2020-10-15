package com.brycecorbitt.artsyapp.ui.preferences

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException

class PreferencesViewModel private constructor(context: Context) : ViewModel() {
    private var unit: String = "mi"
    private var radius: Float = 5F
    private var sortBy: String = "proximity"
    private var lat: Float = 0.0F //user's location later
    private var lon: Float = 0.0F //user's location later
    private var locationButtonText: String = "Enter a Location:"

    val currentUnit: String
        get() = unit
    val CurrentSortType: String
        get() = sortBy
    val currentRadius: Float
        get() = radius
    val currentLat: Float
        get() = lat
    val currentLon: Float
        get() = lon
    val currentLocationButtonText: String
        get() = locationButtonText
    fun setCurrentUnit(u: String) { unit = u }
    fun setCurrentSortType(type: String) { sortBy = type }
    fun setRadius(rad: Float) { radius = rad }
    fun setLat(lt: Float) { lat = lt}
    fun setLong(lg: Float) { lon = lg }
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