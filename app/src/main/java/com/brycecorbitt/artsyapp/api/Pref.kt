package com.brycecorbitt.artsyapp.api

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalStateException

class Pref(context: Context?) {
    var sharedPreferences: SharedPreferences? = context!!.getSharedPreferences("cookie", Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor? = sharedPreferences?.edit()

    companion object {
        private var INSTANCE: Pref? = null

        fun get(context: Context?): Pref? {
            if (INSTANCE == null) {
                INSTANCE = Pref(context)
            }
            return INSTANCE
        }
    }
}