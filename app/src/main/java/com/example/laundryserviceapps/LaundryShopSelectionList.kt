package com.example.laundryserviceapps

import android.content.Context
import android.content.Intent
import android.location.*
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.Adapter.LaundryShopSelectionRecyclerView
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_laundry_shop_list.*
import kotlinx.android.synthetic.main.activity_laundry_shop_selection_list.*

import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class LaundryShopSelectionList : AppCompatActivity(), LocationListener {

    private var poscode: String = ""
    lateinit var handler: SQLiteHelper
    private lateinit var locationManager: LocationManager
    var lstLS = ArrayList<Laundry_Shop>()
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var rvlaundryshop_selection: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laundry_shop_selection_list)

        var intent = intent
        val get_shop1 = intent.getStringExtra("promo_name1")
        val get_address1 = intent.getStringExtra("discount1")

        lblgetpromo_name4.setText(get_shop1)
        lblgetpromo_discount4.setText(get_address1)

        handler = SQLiteHelper(this)
        create_laundry_shop()

        imgback14.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this)
            mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
            mAlertDialog.setTitle("INFO")
            mAlertDialog.setMessage("Are you sure want to cancel the order? \nOnce you cancelled the order, all the services are ordered will not be saved")
            mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                finish()
            }
            mAlertDialog.setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.setNeutralButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
            mAlertDialog.show()
        }
        btnNearbyShop.setOnClickListener {
            checkGps_Network()
        }
        checkGps_Network()

    }

    private fun checkGps_Network() {
        var lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false


        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (ex: Exception) {
        }

        if (!gps_enabled) {
            // notify user
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setMessage("For a better experience,please turn on device location and internet, which uses Google's location service.")
                setPositiveButton("Open location setting") { _, _ ->
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                setNegativeButton("Cancel", null)
                show()
            }
        } else {
            LocationDetect()
            btnNearbyShop.visibility = View.INVISIBLE
        }


    }

    private fun LocationDetect() {
        try {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5f,
                this
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

    }

    override fun onLocationChanged(location: Location?) {
        Toast.makeText(this, "Detecting location.....", Toast.LENGTH_LONG).show()
        Log.i("LocationLL", ("${location!!.latitude} longitude:${location.longitude}").toString())
        getAddress(LatLng(location!!.latitude, location.longitude))
        locationManager.removeUpdates(this)

    }

    private fun getAddress(latLng: LatLng) {
        // 1

        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            // 3
            if (addresses != null && addresses.isNotEmpty()) {
                address = addresses[0]
                poscode =
                    if (address.postalCode == null || address.postalCode.isEmpty()) "" else address.postalCode
            }
            Toast.makeText(this, "Load nearby laundry shop", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }


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

    fun create_laundry_shop() {
        try {
            // val dbhandler = SQLiteHelper(this)
            lstLS = handler.getLaundryShop()
            adapter = LaundryShopSelectionRecyclerView(this, lstLS)
            rvlaundryshop_selection = findViewById(R.id.rvlaundryshop_selection)
            rvlaundryshop_selection.adapter = adapter
            rvlaundryshop_selection.layoutManager = LinearLayoutManager(this)

        } catch (e: Exception) {
            Log.i("error", e.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    var filteredLaundryShop = ArrayList<Laundry_Shop>()

                    if (newText!!.isNotEmpty()) {
                        for (i in 0..lstLS.size - 1) {
                            if (lstLS.get(i).laundry_shop_Address.toLowerCase(Locale.getDefault())
                                    .contains(newText)
                            ) {

                                filteredLaundryShop.add(lstLS[i])
                            }
                        }
                        adapter = LaundryShopSelectionRecyclerView(
                            this@LaundryShopSelectionList,
                            filteredLaundryShop
                        )
                        rvlaundryshop_selection.adapter = adapter
                    } else {
                        adapter =
                            LaundryShopSelectionRecyclerView(this@LaundryShopSelectionList, lstLS)
                        rvlaundryshop_selection.adapter = adapter
                    }
                    return true
                }


            })
            if(poscode!=null){
            searchView.setQuery(poscode,true)
            searchView.visibility=View.INVISIBLE
            }
        }

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun onQueryTextChange(newText: String?): Boolean {
//        TODO("Not yet implemented")
//    }
}
