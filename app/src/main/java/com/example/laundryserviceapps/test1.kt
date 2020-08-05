package com.example.laundryserviceapps

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.test1.*
import java.util.*

class test1 : AppCompatActivity(),LocationListener {
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: Location
    private var locationUpdateState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test1)

        val curent: Date = Calendar.getInstance().time
        editTextTextPersonName2.setText(curent.toString())

        buttontest1.setOnClickListener {
            val i = Intent(this, MapsSearch::class.java)
            startActivity(i)
        }
        var lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
       var  gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(ex:Exception ) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(ex:Exception) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
             with(builder)
             {
                 setMessage("For a better experience, turn on device location, which uses Google's location service.")
                 setPositiveButton("Open location setting"){ dialog, _ ->
                     context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS));
                 }
                 setNegativeButton("Cancel",null)
                 show()
             }





        }
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(p0: LocationResult) {
//                super.onLocationResult(p0)
//
//                lastLocation = p0.lastLocation
//                Toast.makeText(
//                    this@test1,
//                    LatLng(lastLocation.latitude, lastLocation.longitude).toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//        setUpMap()
//        createLocationRequest()
    }

    private fun createLocationRequest() {
        // 1
        locationRequest = LocationRequest()
        // 2
        locationRequest.interval = 10000
        // 3
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // 4
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
    }
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

    }

    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    override fun onLocationChanged(p0: Location?) {
        TODO("Not yet implemented")
    }


}