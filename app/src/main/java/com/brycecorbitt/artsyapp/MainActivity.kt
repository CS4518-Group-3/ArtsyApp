package com.brycecorbitt.artsyapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult.TIMEOUT
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {
    var pref: Pref? = null
    private lateinit var auth: GoogleAuth
    private lateinit var apiCaller: ResponseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = Pref(this)
        super.onCreate(savedInstanceState)
        // init Google Authentication Class
        val intent : Intent = Intent(this, LocationService::class.java)
        if(Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                initLocationService()
            }

        } else {
            initLocationService()
        }


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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                initLocationService()
            }
            else {
                Toast.makeText(this, "Wrong choice", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val token = auth.account?.idToken
        if (token != null) {
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
        } else {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(R.string.web_oauth_clientid.toString())
                .requestEmail()
                .build()
            auth.signInClient = GoogleSignIn.getClient(this, gso)
            auth.account = GoogleSignIn.getLastSignedInAccount(this)
        }
    }

    fun initLocationService() {
        val intent = Intent(this, LocationService::class.java)
        startService(intent)
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
