package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Color
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
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.product_retailer_registration_page.*
import kotlinx.android.synthetic.main.product_retailer_registration_page.EditTxtAddress
import kotlinx.android.synthetic.main.product_retailer_registration_page.btnUpdate
import kotlinx.android.synthetic.main.product_retailer_registration_page.imageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.regex.Pattern


class product_retailRegistration : AppCompatActivity() {

    val arrayList = ArrayList<String>()

    private var strShopName: String? = null
    private var strShopStreet: String? = null
    private var arrayListServices: ArrayList<String>? = null
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
        val view = this.currentFocus


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

        editTextPoscode
        btnPicBrowse.setOnClickListener {
            selectImage()
        }

        btnUpdate.setOnClickListener {
            if (fieldValidation()) {
                dialogBuilder()
            }

        }

        btnCancel.setOnClickListener {

            val intent =
                Intent(this@product_retailRegistration, product_laundryMainMenu::class.java)

            startActivity(intent)
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
//          spinnerState.onItemSelectedListener=object:

//          AdapterView.OnItemSelectedListener{
//              override fun onNothingSelected(parent: AdapterView<*>?) {
//                  TODO("Not yet implemented")
//              }
//
//              override fun onItemSelected(
//                  parent: AdapterView<*>?,
//                  view: View?,
//                  position: Int,
//                  id: Long
//              ) {
//
//
//
//              }
//          }
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
        val street = EditTxtAddress.text.toString()


        return when {
            street.isNotEmpty() -> {
                this.strShopStreet = street
                EditTxtAddress.error = null
                true
            }
            else -> {
                this.strShopStreet = ""
                EditTxtAddress.error = "The address field cannot be empty"
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
        val poscodeLength=poscode?.length
        if (poscodeLength != null) {
            if (poscodeLength >= 5) {
                return true
            } else {
                editTextPoscode.error = "please fill in the poscode value"
                return  false
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
        val imageValid=imageViewFieldValidation()
        val stateValidation=shopStateFieldValidation()
        val poscodeValidation= shopPoscodeFieldValidation()
        val addressValid = shopStreetFieldValidation()
        return if (!this.nameValid || !addressValid || !contactTelNoValid
            || !this.contactPersonValid || !imageValid || !stateValidation ||!poscodeValidation
        ) {
            Toast.makeText(
                this@product_retailRegistration,
                "Please complete compulsory field above",
                Toast.LENGTH_LONG
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
        val state=spinnerState.selectedItem.toString()
        val poscode=editTextPoscode.text.toString()
        val shopAddress= "${this.strShopStreet},@@$poscode,@@$state"
        val lShopList = product_LaundryShopModelClass(
            null, this.strShopName, null, shopAddress, this.byteArrayImage,
            null, this.strContactPerson, this.strShopTelNo
        )
        try {

            val dBHelper = product_databaseHandler(this)
            dBHelper.onCreateLaundryShop()

            val stmt = dBHelper.addLaundryShop(lShopList)
            if (stmt == null) {
                Toast.makeText(
                    this@product_retailRegistration,
                    "The shopName is duplicate",
                    Toast.LENGTH_SHORT
                ).show()

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
        EditTxtAddress.text = null
        editTextContactPerson.text = null
        editTextTelNo.text = null
        editTextPoscode.text = null
        spinnerState.getItemAtPosition(0)
        imageView.setImageResource(0)
        editTextContactPerson.error = null
    }


}