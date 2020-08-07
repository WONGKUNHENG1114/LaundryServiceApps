package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.location.*
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.laundryserviceapps.ClassModel.product_LaundryShopModelClass
import com.example.laundryserviceapps.DatabaseHandler.product_databaseHandler
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.product_retailer_registration_page.*
import kotlinx.android.synthetic.main.product_retailer_registration_page.EditTxtStreet
import kotlinx.android.synthetic.main.product_retailer_registration_page.btnPicBrowse
import kotlinx.android.synthetic.main.product_retailer_registration_page.btnUpdate
import kotlinx.android.synthetic.main.product_retailer_registration_page.editTextContactPerson
import kotlinx.android.synthetic.main.product_retailer_registration_page.editTextPoscode
import kotlinx.android.synthetic.main.product_retailer_registration_page.editTextTelNo
import kotlinx.android.synthetic.main.product_retailer_registration_page.imageView
import kotlinx.android.synthetic.main.product_retailer_registration_page.scrollViewId
import kotlinx.android.synthetic.main.product_retailer_registration_page.spinnerState
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.regex.Pattern


class product_retailRegistration : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var strShopName: String? = null
    private var strShopStreet: String? = null
    private var strContactPerson: String? = null
    private var strShopTelNo: String? = null
    private var filePath: File? = null
    private var byteArrayImage: ByteArray? = null
    private var poscode: String? = null
    private var stateSelected: String? = null
    private var nameValid: Boolean = false

    private var contactPersonValid: Boolean = false

    companion object {
        private val PICK_IMAGE = 1000


    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_retailer_registration_page)


        scrollViewId.setOnTouchListener { v, _ ->
            hideSoftKeyboard(v)
            false
        }

        EditTextShopName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
//                Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
                return

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nameValid = shopNameFieldValidation()
            }

        })


        editTextContactPerson.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
//                Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
                return

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                contactPersonValid = contactPersonValidation()
            }
        })

        editTextTextArea.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                shopAreaFieldValidation()
            }
        }

        editTextPoscode
        btnPicBrowse.setOnClickListener {
            selectImage()
        }

        btnUpdate.setOnClickListener {
            if (fieldValidation()) {
                dialogBuilder()
            }

        }


        btnGCL.setOnClickListener {
            checkGps_Network()

        }

        val list = resources.getStringArray(R.array.state_array)
        list.sort()
        ArrayAdapter.createFromResource(
            this,
            R.array.state_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerState.adapter = arrayAdapter

        }


    }

    private fun checkGps_Network() {
        var lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false


        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (ex: Exception) {
        }

        if (!gps_enabled ) {
            // notify user
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setMessage("For a better experience, turn on device location and internet, which uses Google's location service.")
                setPositiveButton("Open location setting") { dialog, _ ->
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                setNegativeButton("Cancel", null)
                show()
            }


        } else {
            LocationDetect()
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
        getAddress(LatLng(location!!.latitude, location.longitude))
        locationManager.removeUpdates(this)

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

    private fun getAddress(latLng: LatLng) {
        // 1
        val list = resources.getStringArray(R.array.state_array)
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var street:String?=""
        var postcode:String?=""
        var area:String?=""
        var state :String?=""

        var position = 0
        list.sort()
        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//            addressText+=","+addresses[0].postalCode
//            addressText+=  addresses[0].locality
//            addressText+= "," +addresses[0].adminArea
            // 3
            if (addresses != null && addresses.isNotEmpty()) {
                address = addresses[0]
                street = if (address.subThoroughfare == null || address.subThoroughfare.isEmpty()) "UNITNUMBER" else address.subThoroughfare
                street += if (address.thoroughfare == null || address.thoroughfare.isEmpty()  ) ",STREET" else "," + addresses[0].thoroughfare
                postcode = if (address.postalCode==null || address.postalCode.isEmpty()  ) "" else address.postalCode
                area = if (address.locality==null || address.locality.isEmpty() ) "" else address.locality
                state= if (address.adminArea==null || address.adminArea.isEmpty()  ) "" else address.adminArea

            }
            Toast.makeText(this, "Filled location", Toast.LENGTH_SHORT).show()
            for (i in list.indices) {
                if (list[i] == state) {
                    position = i + 1
                    break
                }

            }


            EditTxtStreet.setText(street)
            editTextPoscode.setText(postcode)
            editTextTextArea.setText(area)
            spinnerState.setSelection(position)
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
    }


    private fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    private fun shopNameFieldValidation(): Boolean {
        val strShopName = EditTextShopName.text.toString()
        val sPattern =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\s_]*")//match the pattern insert in the shop name
        val match = sPattern.matcher(strShopName)
        if (strShopName.isNotEmpty() && match.find()) {
            this.strShopName = strShopName
            EditTextShopName.error = null
            return true
        } else if (strShopName.isEmpty()) {
            this.strShopName = ""

            return false
        } else {
            this.strShopName = ""
            EditTextShopName.error =
                "The shop name must start with letter and no special character consist"
            return false
        }


    }

    private fun shopStreetFieldValidation(): Boolean {
        val street = EditTxtStreet.text.toString()


        return if (street.isEmpty()) {
            this.strShopStreet = ""
            EditTxtStreet.error = "The street field cannot be empty"
            false
        } else if (street.contains("UNITNUMBER") || street.contains("STREET")) {
            EditTxtStreet.error = "Please fill in appropriate unit no. and street field"
            false
        } else {
            this.strShopStreet = street
            EditTxtStreet.error = null
            true

        }


    }
    private fun shopAreaFieldValidation(): Boolean {
        val area = editTextTextArea.text.toString()
        val sPattern =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\s_]*")//match the pattern insert in the shop name
        val match = sPattern.matcher(area)
        if (area.isNotEmpty() && match.find()) {
            editTextTextArea.error = null
            return true
        } else if (area.isEmpty()) {
            editTextTextArea.error =
                "The area cannot be null."
            return false
        } else {
            editTextTextArea.error =
                "The shop name must start with letter and no special character consist"
            return false
        }


    }
    private fun shopStateFieldValidation(): Boolean {
        val selectedItem = spinnerState.selectedItemPosition
        return if (selectedItem != 0) {
            stateSelected = spinnerState.getItemAtPosition(selectedItem).toString()
            true
        } else
            false


    }

    private fun shopPostcodeFieldValidation(): Boolean {
        poscode = editTextPoscode.text.toString()
        val poscodeLength = poscode?.length
        if (poscodeLength != null) {
            if (poscodeLength >= 5) {
                return true
            } else {
                editTextPoscode.error = "please fill in the poscode value"
                return false
            }

        }
        editTextPoscode.error = "please fill in the poscode value"
        return false
    }


    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Choose from Gallery", "Cancel")
        val alertDialog1 = AlertDialog.Builder(this@product_retailRegistration)
        with(alertDialog1)
        {
            setTitle("AddPhoto")
            setItems(options) { dialog: DialogInterface, item: Int ->
                when {
                    options[item] == "Choose from Gallery" -> {
                        val intent = Intent(
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )

//                        intent.type = "image/*"
                        startActivityForResult(intent, PICK_IMAGE)
                    }
                    options[item] == "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            }
        }
        alertDialog1.show()

    }

    private fun imageViewFieldValidation(): Boolean {
        if (imageView.drawable != null) {
            txtViewImageNote.visibility = View.INVISIBLE
            imageView.visibility = View.VISIBLE
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            this.byteArrayImage = getBitmapAsByteArray(bitmap)
            return true
        } else {
            txtViewImageNote.setTextColor(Color.RED)
            txtViewImageNote.visibility = View.VISIBLE
            imageView.visibility = View.INVISIBLE
            return false
        }
    }

    private fun contactPersonValidation(): Boolean {
        val strContactPerson = editTextContactPerson.text.toString()
        val sPattern =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*")//match the pattern insert in the shop name
        val match = sPattern.matcher(strContactPerson)
        return if (strContactPerson.isNotEmpty() && match.find()) {
            this.strContactPerson = strContactPerson
            editTextContactPerson.error = null
            true
        } else {
            this.strContactPerson = ""
            editTextContactPerson.error = "The contact person must start with letter"
            false
        }
    }

    private fun contactTelNoValidation(): Boolean {
        var telNo: String = editTextTelNo.text.toString()
        val sPattern =
            Pattern.compile("^[1]([0-9]{8,9}\$)")//match the pattern insert in the shop name
        val altSPattern =
            Pattern.compile("^[0][1]([0-9]{8,9}\$)")//match the pattern insert in the shop name
        val match = sPattern.matcher(telNo)
        val altMatch = altSPattern.matcher(telNo)
        if (match.find()) {
            strShopTelNo = "60$telNo"
            return true
        } else if (altMatch.find()) {

            telNo = telNo.replace("0", "")
            editTextTelNo.setText(telNo)
            strShopTelNo = "60$telNo"
            return true
        } else {
            strShopTelNo = ""
            editTextTelNo.error = "Please follow national telephone format(ie.178557499)"
            return false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            if (requestCode == 1000) {
                imageView.setImageURI(data?.data)
                val imageUri = data?.data
                this.filePath = File(imageUri?.path)
                txtViewImageNote.visibility = View.INVISIBLE
                imageView.visibility = View.VISIBLE
            }

        }

    }

    private fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    private fun fieldValidation(): Boolean {
        val contactTelNoValid = contactTelNoValidation()
        val imageValid = imageViewFieldValidation()
        val areaValidation=shopAreaFieldValidation()
        val stateValidation = shopStateFieldValidation()
        val poscodeValidation = shopPostcodeFieldValidation()
        val streetValid = shopStreetFieldValidation()
        return if (!this.nameValid || !streetValid || !contactTelNoValid || !areaValidation
            || !this.contactPersonValid || !imageValid || !stateValidation || !poscodeValidation
        ) {
            Toast.makeText(
                this@product_retailRegistration,
                "Please complete compulsory field above",
                Toast.LENGTH_SHORT
            ).show()
            false
        } else
            true

    }

    private fun dialogBuilder() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Add Profile Confirmation")
            setMessage("Confirm to add new product profile?")
            setPositiveButton("Confirm") { dialog, which ->
                //insert into database
                insertData()

            }

            setNegativeButton("Discard") { dialog, which ->
                //clear all data
                clearDataConfirmation()
            }
            setNeutralButton("Cancel") { dialog, which ->
                //do nothing
                dialog.dismiss()
            }
            show()
        }


    }

    private fun insertData() {
        val pref: SharedPreferences =
            this.getSharedPreferences("retailer_user_details", MODE_PRIVATE)
        val RetailerUsername = pref.getString("username", null)
        val state = spinnerState.selectedItem.toString()
        val poscode = editTextPoscode.text.toString()
        val area=editTextTextArea.text.toString()
        val shopAddress = "${this.strShopStreet},$poscode $area,$state"

        val lShopList =
            product_LaundryShopModelClass(
                null, this.strShopName, null, shopAddress, this.byteArrayImage,
                null, this.strContactPerson, this.strShopTelNo, RetailerUsername.toString()
            )
        try {

            val dBHelper =
                product_databaseHandler(
                    this
                )
            dBHelper.onCreateLaundryShop()

            val stmt = dBHelper.addLaundryShop(lShopList)
            if (stmt == null) {
                EditTextShopName.error="Duplicate dobi name,please enter new name. "
              Toast.makeText(this@product_retailRegistration,"Duplicate dobi name, please check or enter new name",Toast.LENGTH_SHORT).show()
                this.nameValid=false
            } else {
                Toast.makeText(
                    this@product_retailRegistration,
                    "Laundry shop added",
                    Toast.LENGTH_SHORT
                ).show()
                txtViewImageNote.visibility = View.VISIBLE
                txtViewImageNote.setTextColor(Color.GRAY)
                imageView.visibility = View.INVISIBLE
                clearData()
                this.nameValid=true
                val i = Intent(this@product_retailRegistration, product_laundryMainMenu::class.java)
                startActivity(i)
            }

        } catch (e: Exception) {
            Log.i("error", e.toString())
        }
    }

    private fun clearDataConfirmation() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Clear Data")
            setMessage("Are you sure to clear all field?")
            setPositiveButton("Yes") { dialog, which ->
                clearData()
            }

            setNegativeButton("NO") { dialog, which ->
                //clear all data
                dialog.dismiss()
            }
            show()
        }

    }

    private fun clearData() {
        EditTextShopName.text = null
        EditTxtStreet.text = null
        editTextContactPerson.text = null
        editTextTelNo.text = null
        editTextPoscode.text = null
        spinnerState.getItemAtPosition(0)
        imageView.setImageResource(0)
        editTextContactPerson.error = null
    }


}