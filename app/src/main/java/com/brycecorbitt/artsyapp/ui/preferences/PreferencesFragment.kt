package com.brycecorbitt.artsyapp.ui.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brycecorbitt.artsyapp.R

class PreferencesFragment : Fragment() {

    private lateinit var settingsViewModel: PreferencesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProviders.of(this).get(PreferencesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_preferences, container, false)
        val textView: TextView = root.findViewById(R.id.text_preferences)
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}