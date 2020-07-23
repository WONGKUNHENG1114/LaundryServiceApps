package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_add_laundry_shop__page.*
import java.text.SimpleDateFormat
import java.util.*

class AddLaundryShop_Page : AppCompatActivity() {
    lateinit var handler: SQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_laundry_shop__page)

        handler = SQLiteHelper(this)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        lblshop_date_establish.text = formatedDate.toString()

        imgback8.setOnClickListener {
            onBackPressed()
        }

        btnsave.setOnClickListener {
            addLaundryShop()
        }

        btnbacktomain_page.setOnClickListener {

        }

    }

    fun addLaundryShop(){
//        handler.onCreateLaundryShop()
//        handler.onDropLaundryShop()
        val shopName = edtShopName.text.toString()
        val shopAddress = edtAddressShop.text.toString()
        val shopContactPerson = edtContactPerson.text.toString()
        val PhoneNo = edtPhoneNo.text.toString()
        val phoneNoDigit = lblphone_no.text.toString()
        val dateEstablish = lblshop_date_establish.text.toString()
        val GetPhoneNo = "$phoneNoDigit$PhoneNo"

        if(shopName == ""){
            edtShopName.error = "The compulsory fields cannot be empty, please enter the laundry shop"
        }else if (shopAddress == ""){
            edtAddressShop.error = "The compulsory fields cannot be empty, please enter the laundry shop address"
        }else if (shopContactPerson == ""){
            edtContactPerson.error = "The compulsory fields cannot be empty, please enter the contact person"
        }else if (PhoneNo == "" || PhoneNo.length > 9){
            edtPhoneNo.error = "Please enter the valid phone number"
        }else {
            val LS = Laundry_Shop()
            LS.shopID = 0
            LS.laundry_shop_name = shopName
            LS.laundry_shop_Address = shopAddress
            LS.shop_status = "Active"
            LS.datecreated = dateEstablish
            LS.contact_person = shopContactPerson
            LS.phone_number = GetPhoneNo

            handler.addLaundryShop(LS)
            Toast.makeText(this,"Laundry shop has been added successfully.", Toast.LENGTH_LONG).show()
            val intent = Intent(this,LaundryShopSelectionList::class.java)
            startActivity(intent)

            edtShopName.setText("")
            edtAddressShop.setText("")
            edtContactPerson.setText("")
            edtPhoneNo.setText("")
        }
    }


}