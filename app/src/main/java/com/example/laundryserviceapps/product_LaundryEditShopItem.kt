package com.example.laundryserviceapps


import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
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
import kotlinx.android.synthetic.main.product_laundry_editshop_item.*
import kotlinx.android.synthetic.main.product_laundry_editshop_item.btnGCL
import kotlinx.android.synthetic.main.product_laundry_editshop_item.btnPicBrowse
import kotlinx.android.synthetic.main.product_laundry_editshop_item.btnUpdate
import kotlinx.android.synthetic.main.product_laundry_editshop_item.editTextContactPerson
import kotlinx.android.synthetic.main.product_laundry_editshop_item.editTextPoscode
import kotlinx.android.synthetic.main.product_laundry_editshop_item.editTextTelNo
import kotlinx.android.synthetic.main.product_laundry_editshop_item.editTextTextArea
import kotlinx.android.synthetic.main.product_laundry_editshop_item.imageView
import kotlinx.android.synthetic.main.product_laundry_editshop_item.scrollViewId
import kotlinx.android.synthetic.main.product_laundry_editshop_item.spinnerState
import kotlinx.android.synthetic.main.product_retailer_registration_page.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Arrays.sort
import java.util.Collections.sort
import java.util.regex.Pattern


class product_LaundryEditShopItem : AppCompatActivity(),LocationListener {
    private lateinit var locationManager: LocationManager
    private var strShopName: String? = null
    private var strShopStreet: String? = null
    private var strContactPerson: String? = null
    private var strShopTelNo: String? = null
    private var filePath: File? = null
    private var byteArrayImage: ByteArray? = null
    private var poscode: String? = null
    private var area: String? = null
    private var stateSelected: String? = null
    private var adapter:ArrayAdapter<CharSequence>?=null
    private var contactPersonValid: Boolean = false

    companion object {
        private val PICK_IMAGE = 1000

    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_laundry_editshop_item)

        val intent = intent

        Toast.makeText(this, "come to edit shop page", Toast.LENGTH_SHORT).show()
        strShopName = intent.getStringExtra("shopName")
        editTextShopName.setText(strShopName)
        val lShopList=retrieveData()

        scrollViewId.setOnTouchListener { v, _ ->
            hideSoftKeyboard(v)
            false
        }
//disableShopName
        editTextShopName.isEnabled = false

//trace contact person exception
        editTextContactPerson.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                return

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                contactPersonValid = contactPersonValidation()
            }

        })

        editTextPoscode
        btnPicBrowse.setOnClickListener {
            selectImage()
        }

        btnUpdate.setOnClickListener {
            if (fieldValidation()) {
                dialogBuilder()
            }

        }

        btnDeleteShop.setOnClickListener {
            disableShop()
            Toast.makeText(
                this@product_LaundryEditShopItem,
                "The shop has been deleted",
                Toast.LENGTH_SHORT
            ).show()
            val intent =
                Intent(this@product_LaundryEditShopItem, product_laundryMainMenu::class.java)
            startActivity(intent)
        }


        adapter= ArrayAdapter.createFromResource(
            this,
            R.array.state_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerState.adapter = arrayAdapter

        }
        LaundryShopsetText(lShopList)
        btnGCL.setOnClickListener {
            checkGps_Network()
        }
        btnCancel.setOnClickListener {
                CanceldialogBuilder()

        }
    }

    private fun CanceldialogBuilder() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setMessage("Go back to shop List?")
            setPositiveButton("Yes") { dialog, _ ->
                val intent = Intent(this@product_LaundryEditShopItem,product_viewShopItem::class.java)
                startActivity(intent)
            }
            setNegativeButton("Cancel", null)
            show()
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
        var street: String? = ""
        var postcode: String? = ""
        var area: String? = ""
        var state: String? = ""

        var position = 0
        list.sort()
        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                address = addresses[0]
                street =
                    if (address.subThoroughfare == null || address.subThoroughfare.isEmpty()) "UNITNUMBER" else address.subThoroughfare
                street += if (address.thoroughfare == null || address.thoroughfare.isEmpty()) ",STREET" else "," + addresses[0].thoroughfare
                postcode =
                    if (address.postalCode == null || address.postalCode.isEmpty()) "" else "," + address.postalCode
                area =
                    if (address.locality == null || address.locality.isEmpty()) "" else address.locality
                state =
                    if (address.adminArea == null || address.adminArea.isEmpty()) "" else "," + address.adminArea

            }
            Toast.makeText(this, "Filled location", Toast.LENGTH_SHORT).show()
            for (i in list.indices) {
                if (list[i] == state) {
                    position = i + 1
                    break
                }

            }
            editTextStreet.setText(street)
            editTextPoscode.setText(postcode)
            editTextTextArea.setText(area)
            spinnerState.setSelection(position)
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
    }


    private fun LaundryShopsetText(lShopList: product_LaundryShopModelClass?)
    {
        val arrAddresss=lShopList?.shopAddress?.split(",")

        val contactPerson=lShopList?.contactPerson
        val phoneNo=lShopList?.phoneNo?.substring(2)
        val list = resources.getStringArray(R.array.state_array)
        var street=""
        var poscode=""
        var addrArea =""
        var state =""
        val addressSize=arrAddresss!!.size
        for (x in 0 until addressSize step 1) {

            //3-0=3 3-1=2
            if ((addressSize - x) > 2) {
                Log.i("xAddress1",addressSize.toString())
                Log.i("xiswhat1",x.toString())
                street += arrAddresss[x] + ","
            }
            else if((addressSize - x) ==3)
            {
                street += arrAddresss[x]
            }
                street += arrAddresss[x]
             if ((addressSize - x) == 2) {
                val addObj = arrAddresss[x].split(" ")
                var counter=0
                    while(poscode.isEmpty())
                    {
                        poscode=addObj[counter]
                        counter++
                    }
                    Log.i("xObj1",poscode)

                for (y in counter until addObj.size step 1) {
                    if (addObj.size - x != 0 && addObj[counter].isNotEmpty())
                        addrArea +=  addObj[y] + " "
                    else
                        addrArea += addObj[y]
                }

                    Log.i("xAddArea",addrArea)

            } else if ((addressSize - x) == 1)
            {

                state = arrAddresss[x].trim()

            }
            Log.i("state1",state)
        }
        var position=0
        for (i in list.indices) {

            if(list[i] == state)
            {
                position=i
                Log.i("item2 ",list[i] )
                break
            }
        }
        editTextStreet.setText(street)
        editTextPoscode.setText(poscode)
        editTextTextArea.setText(addrArea)
        spinnerState.setSelection(position)
        editTextContactPerson.setText(contactPerson)
        editTextTelNo.setText(phoneNo)
        imageView.setImageBitmap(lShopList.getImage())
    }

    private fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }


    private fun shopStreetFieldValidation(): Boolean {
        val street = editTextStreet.text.toString()


        return when {
            street.isNotEmpty() -> {
                this.strShopStreet = street
                editTextStreet.error = null
                true
            }
            else -> {
                this.strShopStreet = ""
                editTextStreet.error = "The address field cannot be empty"
                false
            }
        }

    }
    private fun shopAreaFieldValidation(): Boolean {
        val area = editTextTextArea.text.toString()
        val sPattern =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\s_]*")//match the pattern insert in the shop name
        val match = sPattern.matcher(area)
        if (area.isNotEmpty() && match.find()) {
            this.area=area
            editTextTextArea.error = null
            return true
        } else if (area.isEmpty()) {
            this.area=""
            editTextTextArea.error =
                "The area cannot be null."
            return false
        } else {
            this.area=""
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
        val alertDialog1 = AlertDialog.Builder(this@product_LaundryEditShopItem)
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
            imageView.visibility = View.VISIBLE
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            this.byteArrayImage = getBitmapAsByteArray(bitmap)
            return true
        } else {

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
        return when {
            match.find() -> {
                strShopTelNo = "60$telNo"
                true
            }
            altMatch.find() -> {
                telNo = telNo.replace("0", "")
                editTextTelNo.setText(telNo)
                strShopTelNo = "60$telNo"
                true
            }
            else -> {
                strShopTelNo = ""
                editTextTelNo.error = "Please follow national telephone format(ie.178557499)"
                false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            if (requestCode == 1000) {
                imageView.setImageURI(data?.data)
                val imageUri = data?.data
                this.filePath = File(imageUri?.path)
                imageView.visibility = View.VISIBLE
            }

        }

    }

    private fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    private fun fieldValidation(): Boolean {
        val contactTelNoValid = contactTelNoValidation()
        val imageValid = imageViewFieldValidation()
        val areaValidation=shopAreaFieldValidation()
        val stateValidation = shopStateFieldValidation()
        val postcodeValidation = shopPostcodeFieldValidation()
        val streetValid = shopStreetFieldValidation()
        return if ( !streetValid || !contactTelNoValid || !areaValidation
            || !this.contactPersonValid || !imageValid || !stateValidation || !postcodeValidation
        ) {
            Toast.makeText(
                this@product_LaundryEditShopItem,
                "Please complete compulsory field above",
                Toast.LENGTH_LONG
            ).show()
            false
        } else
            true

    }

    private fun dialogBuilder() {
        var check=0
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Update Profile Confirmation")
            setMessage("Confirm to update product profile?")
            setPositiveButton("Confirm") { dialog, which ->
                //insert into database
                check =  updateData()
                val i=Intent(this@product_LaundryEditShopItem,product_viewShopItem::class.java)
                startActivity(i)
            }

            setNeutralButton("Cancel") { dialog, which ->
                //do nothing
                dialog.dismiss()
            }
            show()
        }
            Log.i("checkMe",check.toString())

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun retrieveData(): product_LaundryShopModelClass?{
         val pref: SharedPreferences = this.getSharedPreferences("retailer_user_details", MODE_PRIVATE)
         val retailerUsername= pref.getString("username", null)
        val db =
            product_databaseHandler(
                this
            )
        val dbSqliteDb = db.readableDatabase

        val selectQuery = "SELECT  * FROM ${product_databaseHandler.TABLE_CONTACTS} " +
                "where ${product_databaseHandler.SHOP_NAME}='$strShopName'"
        val cursor: Cursor

        try{
            cursor = dbSqliteDb.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            dbSqliteDb.execSQL(selectQuery)
            return null
        }
        val ShopID:Int
        val ShopName: String
        val ShopEstablishDate: String
        val ShopAddress:String
        val ShopImage: ByteArray
        val ShopStatus: String
        val ShopContact_Person:String
        val ShopPhoneNo:String

        if (cursor.moveToFirst()) {

            do {
                ShopID=cursor.getInt(cursor.getColumnIndex(product_databaseHandler.SHOP_ID))
                ShopName = cursor.getString(cursor.getColumnIndex(product_databaseHandler.SHOP_NAME))
                ShopEstablishDate = cursor.getString(cursor.getColumnIndex(product_databaseHandler.SHOP_ESTABLISH_DATE))
                ShopAddress = cursor.getString(cursor.getColumnIndex(product_databaseHandler.SHOP_ADDRESS))
                ShopImage = cursor.getBlob(cursor.getColumnIndex(product_databaseHandler.SHOP_IMAGE))
                ShopStatus = cursor.getString(cursor.getColumnIndex(product_databaseHandler.SHOP_STATUS))
                ShopContact_Person = cursor.getString(cursor.getColumnIndex(product_databaseHandler.CONTACT_PERSON))
                ShopPhoneNo = cursor.getString(cursor.getColumnIndex(product_databaseHandler.PHONE_NUMBER))
                val formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH)
                Log.e("addressZZZ",ShopAddress)
                return product_LaundryShopModelClass(
                    shopID = ShopID, shopName = ShopName,
                    establishDate = LocalDate.parse(ShopEstablishDate, formatter),
                    shopAddress = ShopAddress,
                    shopImage = ShopImage,
                    shopStatus = ShopStatus,
                    contactPerson = ShopContact_Person,
                    phoneNo = ShopPhoneNo,
                    retailerUser = retailerUsername.toString())


            } while (cursor.moveToNext())
        }

        return null

    }

        fun updateData():Int {
            this.strShopStreet=editTextStreet.text.toString()
            this.poscode=editTextPoscode.text.toString()
            this.area=editTextTextArea.text.toString()
            this.stateSelected=spinnerState.selectedItem.toString()
            val shopAddress= "${this.strShopStreet}, $poscode $area, $stateSelected"
            val db =
                product_databaseHandler(
                    this
                )
            val dbSqliteDb = db.writableDatabase
            val contentValues = ContentValues()

//                contentValues.put(product_databaseHandler.SHOP_NAME, this.strShopName)//LaundryShopModel shopName
                // contentValues.put(SHOP_ESTABLISH_DATE, lShop.getEstablishDateTime()) //LaundryShopModel EstablishedDate
                contentValues.put(product_databaseHandler.SHOP_ADDRESS, shopAddress) //LaundryShopModel shopAddress
                contentValues.put(product_databaseHandler.SHOP_IMAGE, this.byteArrayImage) //LaundryShopModel shopAddress
                //contentValues.put(SHOP_STATUS,lShop.shopStatus) //LaundryShopModel shopAddress
                contentValues.put(product_databaseHandler.CONTACT_PERSON, this.strContactPerson) //LaundryShopModel shopAddress
                contentValues.put(product_databaseHandler.PHONE_NUMBER, this.strShopTelNo) //LaundryShopModel shopAddress
                // Inserting Row
                val success = dbSqliteDb.update(
                    product_databaseHandler.TABLE_CONTACTS, contentValues,
                    "shopName='${this.strShopName}'",null)
                //2nd argument is String containing nullColumnHack
                db.close() // Closing database connection
            return success

        }

    fun disableShop():Int {
        val db =
            product_databaseHandler(
                this
            )
        val dbSqliteDb = db.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(product_databaseHandler.SHOP_STATUS,"Inactive") //LaundryShopModel shopAddress

        // Inserting Row
        val success = dbSqliteDb.update(
            product_databaseHandler.TABLE_CONTACTS, contentValues,
            "shopName='${this.strShopName}'",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success

    }

}




