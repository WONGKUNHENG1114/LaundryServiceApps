package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import android.location.Location

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.test3.*
import java.io.IOException


class test3 : AppCompatActivity(), LocationListener {
    private lateinit var locationManager: LocationManager
    var address = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test3)
        var address =
            "Jalan Prima Setapak 7 , Taman Setapak , 53000 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur"
        val arrAddresss = address.split(",")
        var a = ""
//        arrAddresss.forEach()
//        {
//            a+=it+"\n"
//        }
//
        val addressSize = arrAddresss.size
        var street = ""
        var poscode = ""
        var area = ""
        var state = ""
        for (x in arrAddresss.indices step 1) {

            //3-0=3 3-1=2
            if ((addressSize - x) > 2)
                street += arrAddresss[x] + ", "
            else if ((addressSize - x) == 2) {
                val addObj = arrAddresss[x].split(" ")
                poscode = addObj[0]
                for (y in 1 until addObj.size step 1) {
                    area += if (addObj.size - x != 0)
                        addObj[y] + " "
                    else
                        addObj[y]
                }
            } else if ((addressSize - x) == 1)
                state = arrAddresss[x]

        }
        textView69.text = "$street  "
        Toast.makeText(this, "$street,\n$poscode $area,$state", Toast.LENGTH_LONG).show()
        buttontext3.setOnClickListener {
            LocationDEtect()
        }
    }

    private fun LocationDEtect() {
        try {

            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5f,
                this@test3
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }


    }

    override fun onLocationChanged(location: Location?) {

        address = getAddress(LatLng(location!!.latitude, location.longitude))
        if (address == null || address.isEmpty()) {
            Toast.makeText(
                this,
                "Cannot locate your device location",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            textView69.text = address
            locationManager.removeUpdates(this)
        }


    }

    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""
        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            // 3
            if (addresses != null && addresses.isNotEmpty()) {
                address = addresses[0]
//                addressText = address.getAddressLine(0)
                addressText += if (addresses[0].subThoroughfare == null) "UNIT NUMBER" else addresses[0].subThoroughfare
                addressText += if (addresses[0].thoroughfare == null) ",STREET" else "," + addresses[0].thoroughfare
                addressText += "," + addresses[0].subLocality
                addressText += "," + addresses[0].postalCode
                addressText += addresses[0].locality
                addressText += "," + addresses[0].adminArea

            }
//            if(addressText.isEmpty())

        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        return
    }

    override fun onProviderEnabled(provider: String?) {
        Toast.makeText(this, "Enabled GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    override fun onProviderDisabled(provider: String?) {
        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

}