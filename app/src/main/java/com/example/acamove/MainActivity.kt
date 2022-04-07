package com.example.acamove

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        private const val REQUEST_CODE_AUTOCOMPLETE_FROM = 1
        private const val REQUEST_CODE_AUTOCOMPLETE_TO = 2
        private const val TAG = "MainActivity"

    }
    private lateinit var mMap: GoogleMap
    private  var mMarkerFrom: Marker? = null
    private  var mMarkerTo: Marker? = null
    private lateinit var mFromLng: LatLng
    private lateinit var mToLng: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       setupMaps()

        setupPlaces()
    }
    private fun setupMaps(){
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    private fun setupPlaces(){
        // Initialize the SDK
        Places.initialize(applicationContext, getString(R.string.android_sdk_places_api_key))

        btnFrom.setOnClickListener{
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_FROM)
        }
        btnTo.setOnClickListener {
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_TO)
        }
    }

    private fun startAutocomplete(requestCode:Int){
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.ID, Place.Field.NAME)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent,requestCode )

    }
    /*private fun printInfo(place: Place){

        Log.i(TAG, "Place : $place")
        /**
         Log.i(TAG, "Place Name: ${place.name}, ${place.id}")
        Log.i(TAG, "Place Id: ${place.id}")
        Log.i(TAG, "Place LatLng: ${place.latLng}")
        Log.i(TAG, "Place LatLng: ${place.address}")
         *
         * */

    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_FROM) {
            processAutocompleteResult(resultCode, data) {place ->
                tvFrom.text = getString(R.string.mi_ubicacion, place.address)
                place.latLng?.let {
                    mFromLng = it
                }
                addMarker(mFromLng, getString(R.string.marker_title_from))
                setMarkerFrom(mFromLng)
            }
            return
        }else if (requestCode == REQUEST_CODE_AUTOCOMPLETE_TO){
            processAutocompleteResult(resultCode, data){place ->
                tvTo.text = getString(R.string.a_donde_quieres_ir, place.address)
                place.latLng?.let {
                    mToLng = it
                }
                setMarkerTo(mToLng)
                addMarker(mToLng, getString(R.string.marker_title_to))
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun processAutocompleteResult(
        resultCode: Int, data: Intent?,
        callback: (Place)->Unit
    )

    {
        Log.d(TAG, "processAutocompleteResult(resultCode= $resultCode)")
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(data)

                    Log.i(TAG, "Place : $place")
                    callback(place)
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
                // TODO: Handle the error.
                data?.let {
                    val status = Autocomplete.getStatusFromIntent(data)
                    status.statusMessage?.let {
                            message -> Log.i(TAG, message)
                    }
                }
            }

        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //addMarker(sydney, "Sydney")
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    private fun addMarker(latLng: LatLng, title: String): Marker{
        val markerOptions = MarkerOptions()
            .position(latLng)
            .title(title)

       mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
       return mMap.addMarker(markerOptions)
    }
    private fun setMarkerFrom(latLng: LatLng) {

         mMarkerFrom?.remove()

        mMarkerFrom = addMarker(latLng, getString(R.string.marker_title_from))
    }

    private fun setMarkerTo(latLng: LatLng) {

        mMarkerTo?.remove()

        mMarkerTo = addMarker(latLng, getString(R.string.marker_title_to))
    }
}


