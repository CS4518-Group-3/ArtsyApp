package com.brycecorbitt.artsyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brycecorbitt.artsyapp.GoogleAuth
import com.brycecorbitt.artsyapp.R
import com.google.android.gms.common.SignInButton

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private val auth: GoogleAuth = GoogleAuth.instance!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val textView: TextView = root.findViewById(R.id.text_login)
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val loginButton: SignInButton = root.findViewById(R.id.login_button)
        loginButton.apply {
            setOnClickListener {
                auth.loginIntent()
            }
        }
        return root
    }
}