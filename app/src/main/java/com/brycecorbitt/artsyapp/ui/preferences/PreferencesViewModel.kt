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
    private var userLat: Float = 0.0F
    private var userLon: Float = 0.0F
    private var globalLat: Float = 0.0F
    private var globalLon: Float = 0.0F
    private var isLocal: Boolean = true
    private var locationButtonText: String = "Enter a Location:"

    val currentUnit: String
        get() = unit
    val CurrentSortType: String
        get() = sortBy
    val currentRadius: Float
        get() = radius
    val currentUserLat: Float
        get() = userLat
    val currentUserLon: Float
        get() = userLon
    val currentLocationButtonText: String
        get() = locationButtonText
    val currentGlobalLat: Float
        get() = globalLat
    val currentGlobalLon: Float
        get() = globalLon
    val checkIsLocal: Boolean
        get() = isLocal

    fun setCurrentUnit(u: String) {
        unit = u
    }

    fun setCurrentSortType(type: String) {
        sortBy = type
    }

    fun setRadius(rad: Float) {
        radius = rad
    }

    fun setUserLocation(lt: Float, ln: Float) {
        userLat = lt
        userLon = ln
    }


    fun setGlobalLocation(lt: Float, ln: Float) {
        globalLat = lt
        globalLon = ln
    }


    fun setLocationButtonText(s: String) {
        locationButtonText = s
    }

    fun setIsLocal(b: Boolean) {
        isLocal = b
    }

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