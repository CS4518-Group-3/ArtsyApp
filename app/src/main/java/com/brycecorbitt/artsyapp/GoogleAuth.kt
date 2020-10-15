package com.brycecorbitt.artsyapp

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


const val RC_SIGN_IN = 25
private const val TAG = "GoogleAuth"
class GoogleAuth(private val activity: MainActivity, web_client_id: String?) {
    var signInClient: GoogleSignInClient? = null
    var account: GoogleSignInAccount? = null

    // Starts Google SignIn activity, prompting user to sign in with Google account
    fun loginIntent(){
        val intent: Intent = signInClient!!.signInIntent
        activity.startActivityForResult(intent, RC_SIGN_IN)
    }

    // Used when Google SignIn activity responds with account information
    fun authenticate(data: Intent){
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            account = task.getResult(ApiException::class.java)!!
        }
        catch(e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    init {
        instance = this
        if(web_client_id.isNullOrEmpty()){
            Log.w(TAG, "No OAuth Client ID received. App will be unable to authenticate with backend.")
        }
        else{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(web_client_id)
                .requestEmail()
                .build()
            signInClient = GoogleSignIn.getClient(activity, gso)
            account = GoogleSignIn.getLastSignedInAccount(activity)
        }

    }

    // Acts as a Singleton
    companion object {
        var instance: GoogleAuth? = null
    }
}