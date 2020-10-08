package com.brycecorbitt.artsyapp.ui.preferences

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.brycecorbitt.artsyapp.R
import com.google.android.material.slider.Slider
import com.shivtechs.maplocationpicker.LocationPickerActivity
import com.shivtechs.maplocationpicker.MapUtility
import kotlinx.android.synthetic.main.fragment_preferences.*
import java.lang.Exception

const val LOCATIONPICKERREQUEST = 2

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapUtility.apiKey = resources.getString(R.string.your_api_key)
    }
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
        locationButton.text = settingsViewModel.currentLocationButtonText
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
            val i: Intent = Intent(activity, LocationPickerActivity::class.java)
            startActivityForResult(i, LOCATIONPICKERREQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOCATIONPICKERREQUEST) {
            try {
                if(data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    settingsViewModel.setLat(data.getDoubleExtra(MapUtility.LATITUDE, 0.0))
                    settingsViewModel.setLong(data.getDoubleExtra(MapUtility.LONGITUDE, 0.0))
                    var s: String? = data.getBundleExtra("fullAddress").getString("city")
                    if (s != null) {
                        settingsViewModel.setLocationButtonText(s)
                        locationButton.text = settingsViewModel.currentLocationButtonText
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}