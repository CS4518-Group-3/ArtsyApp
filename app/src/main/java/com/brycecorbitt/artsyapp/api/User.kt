package com.brycecorbitt.artsyapp.api

import android.content.Context
import com.brycecorbitt.artsyapp.ui.preferences.PreferencesViewModel
import com.google.gson.annotations.SerializedName
import java.lang.IllegalStateException

class User {
    @SerializedName("created_at")
    lateinit var created_at: String

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("google_id")
    lateinit var google_id: String

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("profile_url")
    var profile_url: String? = null

    @SerializedName("updated_at")
    lateinit var updated_at: String

    companion object {
        private var INSTANCE: User? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = User()
            }
        }

        fun get(): User {
            return INSTANCE
                ?: throw IllegalStateException("PreferencesViewModel must be initialized")
        }
    }
}