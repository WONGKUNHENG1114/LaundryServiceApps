package com.example.laundryserviceapps


import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import kotlinx.android.synthetic.main.product_laundry_editshop_item.*
import kotlinx.android.synthetic.main.product_laundry_editshop_item.btnPicBrowse
import kotlinx.android.synthetic.main.product_laundry_editshop_item.btnUpdate
import kotlinx.android.synthetic.main.product_laundry_editshop_item.editTextContactPerson
import kotlinx.android.synthetic.main.product_laundry_editshop_item.editTextPoscode
import kotlinx.android.synthetic.main.product_laundry_editshop_item.editTextTelNo
import kotlinx.android.synthetic.main.product_laundry_editshop_item.imageView
import kotlinx.android.synthetic.main.product_laundry_editshop_item.scrollViewId
import kotlinx.android.synthetic.main.product_laundry_editshop_item.spinnerState

import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern


class product_LaundryEditShopItem : AppCompatActivity() {

    private var strShopName: String? = null
    private var strShopStreet: String? = null
    private var strContactPerson: String? = null
    private var strShopTelNo: String? = null
    private var filePath: File? = null
    private var byteArrayImage: ByteArray? = null
    private var poscode: String? = null
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

        val list = resources.getStringArray(R.array.state_array)
        list.sort()
        adapter= ArrayAdapter.createFromResource(
            this,
            R.array.state_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerState.adapter = arrayAdapter

        }
        LaundryShopsetText(lShopList)
    }

    private fun LaundryShopsetText(lShopList: product_LaundryShopModelClass?)
    {
        val arrAddresss=lShopList?.shopAddress?.split(",@@")
        val contactPerson=lShopList?.contactPerson
        val phoneNo=lShopList?.phoneNo?.substring(2)
        val list = resources.getStringArray(R.array.state_array)
        val street=arrAddresss?.get(0)
        val poscode=arrAddresss?.get(1)
        val state=arrAddresss?.get(2)


        var position=0
        list.sort()
        for (i in list.indices) {
            if(list[i].equals(state))
            {
                position=i+1
                break
            }
        }
        editTextStreet.setText(street)
        editTextPoscode.setText(poscode)
        spinnerState.setSelection(position)
        editTextContactPerson.setText(contactPerson)
        editTextTelNo.setText(phoneNo)
        imageView.setImageBitmap(lShopList?.getImage())
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

    private fun shopStateFieldValidation(): Boolean {
        val selectedItem = spinnerState.selectedItemPosition
        return if (selectedItem != 0) {
            stateSelected = spinnerState.getItemAtPosition(selectedItem).toString()
            true
        } else
            false


    }

    private fun shopPoscodeFieldValidation(): Boolean {
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
        val stateValidation = shopStateFieldValidation()
        val poscodeValidation = shopPoscodeFieldValidation()
        val streetValid = shopStreetFieldValidation()
        return if ( !streetValid || !contactTelNoValid
            || !this.contactPersonValid || !imageValid || !stateValidation || !poscodeValidation
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
         val RetailerUsername= pref.getString("username", null)
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

                return product_LaundryShopModelClass(
                    shopID = ShopID, shopName = ShopName,
                    establishDate = LocalDate.parse(ShopEstablishDate, formatter),
                    shopAddress = ShopAddress,
                    shopImage = ShopImage,
                    shopStatus = ShopStatus,
                    contactPerson = ShopContact_Person,
                    phoneNo = ShopPhoneNo
                ,retailerUser = RetailerUsername.toString())


            } while (cursor.moveToNext())
        }

        return null

    }

        fun updateData():Int {
            val shopAddress= "${this.strShopStreet},@@$poscode,@@$stateSelected"
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
        val shopAddress= "${this.strShopStreet},@@$poscode,@@$stateSelected"
        val db =
            product_databaseHandler(
                this
            )
        val dbSqliteDb = db.writableDatabase
        val contentValues = ContentValues()

//
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




