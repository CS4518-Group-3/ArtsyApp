package com.brycecorbitt.artsyapp

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*

class LocationService : Service() {

    private lateinit var client: FusedLocationProviderClient
    private lateinit var locationCallback : LocationCallback

    override fun onBind(intent: Intent?): IBinder? {
    return null
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        client = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult : LocationResult) {
                super.onLocationResult(locationResult)
                current_location = locationResult.lastLocation
                Log.d("LATLON", locationResult.lastLocation.latitude.toString() + ", " + locationResult.lastLocation.longitude.toString())
                //Toast.makeText(applicationContext, locationResult.lastLocation.latitude.toString() + ", " + locationResult.lastLocation.longitude.toString(), Toast.LENGTH_LONG).show()
            }
        }
//        locationCallback.onLocationResult() =
//        locationCallback.onLocationResult(){
//
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LATLON", "STARTED")
        requestLocation()
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(){
        var locationRequest : LocationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    companion object {
        var current_location: Location? = null
    }
}
