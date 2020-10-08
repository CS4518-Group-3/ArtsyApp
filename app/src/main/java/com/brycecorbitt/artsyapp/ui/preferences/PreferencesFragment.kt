package com.brycecorbitt.artsyapp.ui.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.brycecorbitt.artsyapp.R
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.fragment_preferences.*

class PreferencesFragment : Fragment() {

    private lateinit var settingsViewModel: PreferencesViewModel
    private lateinit var miRadio: RadioButton
    private lateinit var kmRadio: RadioButton
    private lateinit var unitRadioGroup: RadioGroup
    private lateinit var miSlider: Slider
    private lateinit var kmSlider: Slider
    private lateinit var locationButton: Button
    private lateinit var locationRadio: RadioButton
    private lateinit var popularityRadio: RadioButton
    private lateinit var sortRadioGroup: RadioGroup
    private lateinit var locationTextView: TextView
    private lateinit var logout: Button
    private lateinit var deleteAccount: Button
    private lateinit var emailTextView: TextView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel = PreferencesViewModel.get()
        val root = inflater.inflate(R.layout.fragment_preferences, container, false)
        miRadio = root.findViewById(R.id.milesRadio)
        kmRadio = root.findViewById(R.id.kilometersRadio)
        unitRadioGroup = root.findViewById(R.id.distanceRadioGroup)
        miSlider = root.findViewById(R.id.milesSlider)
        kmSlider = root.findViewById(R.id.kilometersSlider)
        locationButton = root.findViewById(R.id.LocationButton)
        locationRadio = root.findViewById(R.id.locationRadio)
        popularityRadio = root.findViewById(R.id.popularityRadio)
        sortRadioGroup = root.findViewById(R.id.sortRadioGroup)
        locationTextView = root.findViewById(R.id.locationRadiusTextView)
        logout = root.findViewById(R.id.logoutButton)
        deleteAccount = root.findViewById(R.id.deleteAccountButton)
        emailTextView = root.findViewById(R.id.emailTextView)

        emailTextView.text = "xxxxx@gmail.com"
        return root
    }

    override fun onStart() {
        super.onStart()
        unitRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if(i == R.id.milesRadio) {
                miSlider.visibility = View.VISIBLE
                kmSlider.visibility = View.GONE
                locationTextView.text = "Select your location radius for posts (mi):"
                settingsViewModel.setIsCurrentUnitMiles(true)
                settingsViewModel.setRadius(miSlider.value)
            } else {
                miSlider.visibility = View.GONE
                kmSlider.visibility = View.VISIBLE
                locationTextView.text = "Select your location radius for posts (km):"
                settingsViewModel.setIsCurrentUnitMiles(false)
                settingsViewModel.setRadius(kmSlider.value)
            }
        }
        miSlider.addOnChangeListener { slider, value, fromUser ->
            settingsViewModel.setRadius(value)
        }
        kmSlider.addOnChangeListener { slider, value, fromUser ->
            settingsViewModel.setRadius(value)
        }
        sortRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if(i == R.id.locationRadio) {
                settingsViewModel.setIsCurrentSortLocation(true)
            } else {
                settingsViewModel.setIsCurrentSortLocation(false)
            }
        }
        logoutButton.setOnClickListener {
            //
        }
        deleteAccount.setOnClickListener {
            //
        }
        locationButton.setOnClickListener{
            //
        }
    }
}