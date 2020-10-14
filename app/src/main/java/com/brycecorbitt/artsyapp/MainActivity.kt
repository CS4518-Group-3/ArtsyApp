package com.brycecorbitt.artsyapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.brycecorbitt.artsyapp.api.AuthenticationResponse
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.api.Pref

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    var pref: Pref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = Pref.get(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_preferences, R.id.navigation_feed, R.id.navigation_canvas, R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val req: LiveData<AuthenticationResponse?> = ResponseHandler(this).authenticate("eyJhbGciOiJSUzI1NiIsImtpZCI6IjdkYTc4NjNlODYzN2Q2NjliYzJhMTI2MjJjZWRlMmE4ODEzZDExYjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiODAyMjQzNzE0MDIyLW9pNjJtM21jamlldnBwOWRhZGRlNnVoYjFtZXQ1bmxoLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiODAyMjQzNzE0MDIyLW9pNjJtM21jamlldnBwOWRhZGRlNnVoYjFtZXQ1bmxoLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTEwNjYzMzcwNjgwMjE5MDIzMTY2IiwiZW1haWwiOiJrZW5uZXRoZGVzcm9zaWVyc0BnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6IkIyWlpUSW0wUVljNmstTXJXU0FRaFEiLCJuYW1lIjoiS2VubmV0aCBEZXNyb3NpZXJzIiwicGljdHVyZSI6Imh0dHBzOi8vbGg0Lmdvb2dsZXVzZXJjb250ZW50LmNvbS8tNk9PZkN2MEJYOVEvQUFBQUFBQUFBQUkvQUFBQUFBQUFBRkEvQU1adXVjbFFVN2VRZHpzYVRVU3Ezdmo4SFRCbjN1MU5NZy9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiS2VubmV0aCIsImZhbWlseV9uYW1lIjoiRGVzcm9zaWVycyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNjAyNzAzMjQ5LCJleHAiOjE2MDI3MDY4NDksImp0aSI6IjljYWYyYjZmZmRmNzY1N2VkZDI5YjIyYTU2NzRmN2EzM2RiYjYzN2MifQ.hi43yY65fElCx3s1K-PAnItTOZPwxMTX3IHAoD4SRYvoNdR2b2KejFNm3AqeqXhesDICVJTOWGjoG_wzZoW0-iyGHj-i-RUbFsacZZcaAT-XwcX-KrnwC0kyYAmI5UHN4-_OEZXrwEZhxURYrd5Lp8TDQer2KkN-sFEfgeYgxy_URHGfm0SuAlL8fz9EDaVRu2xdvxB6wvkkxLhnklLNBlkYSOQPF4ya6tKGnosEghwQVQNZnP2joypPcstFXGdbXqL1KjBqeQQ9051UqSBtdC1eMkr4BFIiKWFkSVn_rIg9KnAjA1xSb2KoRS63GXNtHUtnd_IhCy7JcBTO57_uaw")
        req.observe(
            this,
            Observer {item ->
                Log.d(TAG, "${item?.authenticated}")
            }
        )

        val req2: LiveData<AuthenticationResponse?> = ResponseHandler(this).checkIfAuthenticated()
        req2.observe(
            this,
            Observer {item ->
                Log.d(TAG, "${item?.authenticated}")
            }
        )
    }
}
