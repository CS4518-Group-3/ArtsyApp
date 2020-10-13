package com.brycecorbitt.artsyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var auth: GoogleAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Make /auth/check API call here to check if user has valid session cookie already.
        // init Google Authentication Class
        auth = GoogleAuth(this, getString(R.string.web_oauth_clientid))

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
                R.id.navigation_preferences, R.id.navigation_feed, R.id.navigation_canvas, R.id.navigation_account, R.id.navigation_login
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
        if (requestCode == RC_SIGN_IN){
            auth.authenticate(data!!)
            // TODO: Add API call here to authenticate with backend using auth.account.idToken
            // After user is authenticated with backend, return to feed (include this function in a callback from API if needed)
            hideLogin()
        }
    }

    private fun showLogin() {
        // Hide Navigation bar
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.visibility = View.GONE
        // Programmatically navigate to login screen
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.navigation_login)
    }

    private fun hideLogin(){
        // Programmatically navigate to home (feed) screen
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.navigation_feed)
        // Show Navigation bar
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.visibility = View.VISIBLE
    }
}
