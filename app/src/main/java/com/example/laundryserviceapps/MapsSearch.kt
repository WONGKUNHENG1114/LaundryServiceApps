package com.example.laundryserviceapps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker

class MapsSearch: FragmentActivity(), OnMapReadyCallback, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private var mMap: GoogleMap? = null
    internal lateinit var mLastLocation: Location
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
    }
    @Synchronized
    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onLocationChanged(location: Location?) {
        TODO("Not yet implemented")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this)
        }
    }
    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("Frag1") != null) {
            supportFragmentManager.popBackStackImmediate("Frag1", 0)
        } else {
            super.onBackPressed()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
}