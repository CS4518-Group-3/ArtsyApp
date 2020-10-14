package com.brycecorbitt.artsyapp.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class HeaderInterceptor(context: Context) : Interceptor {
    private var preferences: Pref? = Pref.get(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        var cookie = preferences?.sharedPreferences?.getString("cookie", "")
        var original: Request = chain.request()
        var request: Request
        if (cookie.equals("")) {
            request = original.newBuilder()
                .build()
        } else {
            request = original.newBuilder()
                .addHeader("Set-Cookie", cookie)
                .build()
        }
        return chain.proceed(request)
    }
}