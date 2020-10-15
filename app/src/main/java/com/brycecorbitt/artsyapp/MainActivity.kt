package com.brycecorbitt.artsyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.brycecorbitt.artsyapp.api.AuthenticationResponse
import com.brycecorbitt.artsyapp.api.Pref
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.api.User
import com.google.android.gms.auth.api.Auth
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    var pref: Pref? = null
    private lateinit var auth: GoogleAuth
    private lateinit var apiCaller: ResponseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        pref = Pref(this)
        super.onCreate(savedInstanceState)
        // init Google Authentication Class
        auth = GoogleAuth(this, getString(R.string.web_oauth_clientid))
        apiCaller = ResponseHandler(this)
        val response: LiveData<AuthenticationResponse?> = apiCaller.checkIfAuthenticated()
        response.observe(
            this,
            Observer { item ->
                if (!item?.authenticated!!) {
                    showLogin()
                } else {
                    var user = User.get()
                    user.set(item.user!!)
                }
            }
        )

        // Hide Title Bar
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        // Navigation initialization
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_preferences,
                R.id.navigation_feed,
                R.id.navigation_canvas,
                R.id.navigation_account,
                R.id.navigation_login
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        // Navigate to login screen if user is not authenticated. Short circuit statement in future if API already has a valid cookie.
//        if(/*!API.authenticated &&*/ auth.account == null){
//            showLogin()
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val token = auth.account?.idToken
        if (requestCode == RC_SIGN_IN) {
            auth.authenticate(data!!)
            val response: LiveData<AuthenticationResponse?> = apiCaller.authenticate(token!!)
            response.observe(
                this,
                Observer { item ->
                    if (item?.authenticated!!) {
                        var user = User.get()
                        user.set(item.user!!)
                        hideLogin()
                    }
                }
            )
            // After user is authenticated with backend, return to feed (include this function in a callback from API if needed)
            hideLogin()
        }
    }

    fun showLogin() {
        // Hide Navigation bar
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.visibility = View.GONE
        // Programmatically navigate to login screen
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.navigation_login)
    }

    private fun hideLogin() {
        // Programmatically navigate to home (feed) screen
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.navigation_feed)
        // Show Navigation bar
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.visibility = View.VISIBLE
    }
}
