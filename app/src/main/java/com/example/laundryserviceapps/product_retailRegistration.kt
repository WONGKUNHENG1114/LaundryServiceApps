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
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.product_add_shop_page.*
import kotlinx.android.synthetic.main.product_add_shop_page.EditTxtAddress
import kotlinx.android.synthetic.main.product_add_shop_page.btnConfirm
import kotlinx.android.synthetic.main.product_add_shop_page.imageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.regex.Pattern


class product_retailRegistration : AppCompatActivity() {
    val KEY_User_Document1 = "doc1"
    private var Document_img1: String = ""
    val arrayList = ArrayList<String>()

    private var strShopName: String? = null
    private var strShopAddress: String? = null
    private var arrayListServices: ArrayList<String>? = null
    private var strContactPerson: String? = null
    private var strShopTelNo: String? = null
    private var filePath: File? = null
    private var byteArrayImage: ByteArray? = null

    companion object {
        private val PICK_IMAGE = 1000


    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_add_shop_page)
        val view = this.currentFocus


        scrollViewId.setOnTouchListener { v, _ ->
            hideSoftKeyboard(v)
            false
        }

        btnPicBrowse.setOnClickListener {
            selectImage()
        }

        btnConfirm.setOnClickListener {
            if (fieldValidation()) {
                dialogBuilder()
            }

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
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*")//match the pattern insert in the shop name
        val match = sPattern.matcher(strShopName)
        return if (strShopName.isNotEmpty() && match.find()) {
            this.strShopName = strShopName
            EditTextShopName.error = null
            true
        } else {
            this.strShopName = ""
            EditTextShopName.error = "The shop name must start with letter"
            Toast.makeText(
                this@product_retailRegistration,
                "Deselect service: hello",
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun shopAddressFieldValidation(): Boolean {
        val address = EditTxtAddress.text.toString()
        if (address.isNotEmpty()) {
            this.strShopAddress = address
            EditTxtAddress.error = null
            return true
        } else
            strShopAddress = ""
        EditTxtAddress.error = "The address field cannot be empty"
        return false
    }

    private fun checkboxServiceFieldValidation(): Boolean {
        return if (this.arrayList.size == 0) {

            txtViewServices.error = "The checkbox must at least one check "
            false
        } else {
            this.arrayListServices = arrayList
            txtViewServices.error = null
            true
        }

    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBoxWashIron -> {
                    if (checked) {
                        this.arrayList.add(view.id.toString())
                        Toast.makeText(
                            this@product_retailRegistration,
                            "Select service: " + view.text,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        this.arrayList.remove(view.id.toString())
                        Toast.makeText(
                            this@product_retailRegistration,
                            "Deselect service: " + view.text,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
                R.id.checkBoxIroning -> {
                    if (checked) {
                        this.arrayList.add(view.id.toString())
                        Toast.makeText(
                            this@product_retailRegistration,
                            "Select service: " + view.text,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        this.arrayList.remove(view.id.toString())
                        Toast.makeText(
                            this@product_retailRegistration,
                            "Deselect service: " + view.text,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.checkBoxWashFold -> {
                    if (checked) {
                        this.arrayList.add(view.id.toString())
                        Toast.makeText(
                            this@product_retailRegistration,
                            "Select service: " + view.text,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        this.arrayList.remove(view.id.toString())
                        Toast.makeText(
                            this@product_retailRegistration,
                            "Deselect service: " + view.text,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }


        }
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
        if (strContactPerson.isNotEmpty() && match.find()) {
            this.strContactPerson = strContactPerson
            editTextContactPerson.error = null
            return true
        } else {
            this.strContactPerson = ""
            editTextContactPerson.error = "The contact person must start with letter"
            return false
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
        val nameValid= shopNameFieldValidation()
        val addressValid= shopAddressFieldValidation()
        val contactTelNoValid=contactTelNoValidation()
        val checkValid= checkboxServiceFieldValidation()
        val personValid= contactPersonValidation()
        val imageValid=imageViewFieldValidation()

        return if (!nameValid || !addressValid || !contactTelNoValid ||
            !checkValid || !personValid || !imageValid) {
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

        val lShopList = product_LaundryShopModelClass(
            null, this.strShopName, null, this.strShopAddress, this.byteArrayImage,
            null, this.strContactPerson, this.strShopTelNo
        )
        try
        {

            val dBHelper = product_databaseHandler(this)
            dBHelper.onCreateLaundryShop()

            val stmt = dBHelper.addLaundryShop(lShopList)
            if (stmt == null) {
                Toast.makeText(
                    this@product_retailRegistration,
                    "The shopName is duplicate",
                    Toast.LENGTH_SHORT
                ).show()

            }
            else
            {
                Toast.makeText(
                    this@product_retailRegistration,
                    "Laundry shop added",
                    Toast.LENGTH_SHORT
                ).show()
                txtViewImageNote.visibility=View.VISIBLE
                txtViewImageNote.setTextColor(Color.GRAY)
                imageView.visibility=View.INVISIBLE
                clearData()
            }

        }catch(e:Exception)
        {
            Log.i("error",e.toString())
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
    private fun clearData()
    {
        EditTextShopName.text = null
        EditTxtAddress.text = null
        editTextContactPerson.text = null
        editTextTelNo.text = null
        imageView.setImageResource(0)
        checkBoxWashFold.isChecked = false
        checkBoxWashIron.isChecked = false
        checkBoxIroning.isChecked = false
    }
//val i = Intent(this, addShopConfirmation::class.java)
//val bundle = Bundle()
//
//bundle.putParcelable("confirmImage", this.byteArrayImage)
//startActivity(i)
    //            bundle.putString("confirmShopName", this.strShopName)
//            bundle.putString("confirmShopAddress", this.strShopAddress)
//            bundle.putString("confirmShopContactPerson", this.strContactPerson)
//            bundle.putString("confirmShopTelNo", this.strShopTelNo)
//            bundle.putStringArrayList("confirmCheckBox", this.arrayListServices)
//            i.putExtras(bundle)
}