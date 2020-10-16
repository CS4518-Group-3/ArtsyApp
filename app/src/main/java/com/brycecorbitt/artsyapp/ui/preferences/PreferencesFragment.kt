package com.brycecorbitt.artsyapp.ui.preferences

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.brycecorbitt.artsyapp.MainActivity
import com.brycecorbitt.artsyapp.R
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.api.User
import com.google.android.material.slider.Slider
import com.shivtechs.maplocationpicker.LocationPickerActivity
import com.shivtechs.maplocationpicker.MapUtility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_preferences.*

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
    private lateinit var profilePic: ImageView
    private lateinit var user: User
    private var appCaller: ResponseHandler = ResponseHandler(context)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapUtility.apiKey = resources.getString(R.string.your_api_key)
        user = User.get()
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
        profilePic = root.findViewById(R.id.profilePic)
        emailTextView = root.findViewById(R.id.emailTextView)
        emailTextView.text = user.email
        Picasso.get().load(user.profile_url).into(profilePic)
        locationButton.text = settingsViewModel.currentLocationButtonText
        return root
    }

    override fun onStart() {
        super.onStart()
        unitRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.milesRadio) {
                miSlider.visibility = View.VISIBLE
                kmSlider.visibility = View.GONE
                locationTextView.text = "Select your location radius for posts (mi):"
                settingsViewModel.setCurrentUnit("mi")
                settingsViewModel.setRadius(miSlider.value)
            } else {
                miSlider.visibility = View.GONE
                kmSlider.visibility = View.VISIBLE
                locationTextView.text = "Select your location radius for posts (km):"
                settingsViewModel.setCurrentUnit("km")
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
            if (i == R.id.locationRadio) {
                settingsViewModel.setCurrentSortType("proximity")
            } else {
                settingsViewModel.setCurrentSortType("popular")
            }
        }
        logoutButton.setOnClickListener {
            appCaller.signOut()
            val activity: Activity? = activity
            if (activity is MainActivity) {
                val myactivity: MainActivity? = activity
                //myactivity?.showLogin()
            }
        }
        deleteAccount.setOnClickListener {
            appCaller.deleteAccount()
            val activity: Activity? = activity
            if (activity is MainActivity) {
                val myactivity: MainActivity? = activity
                //myactivity?.showLogin()
            }
        }
        locationButton.setOnClickListener {
            val i: Intent = Intent(activity, LocationPickerActivity::class.java)
            startActivityForResult(i, LOCATIONPICKERREQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATIONPICKERREQUEST) {
            try {
                if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    settingsViewModel.setLat(data.getFloatExtra(MapUtility.LATITUDE, 0.0F))
                    settingsViewModel.setLong(data.getFloatExtra(MapUtility.LONGITUDE, 0.0F))
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