package com.brycecorbitt.artsyapp

import android.app.Application
import com.brycecorbitt.artsyapp.ui.preferences.PreferencesViewModel

class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesViewModel.initialize(this)
    }
}