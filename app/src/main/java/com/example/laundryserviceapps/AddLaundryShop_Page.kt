package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_add_laundry_shop__page.*
import kotlinx.android.synthetic.main.activity_registration.*
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
            val intent = Intent(this,LaundryShopList::class.java)
            startActivity(intent)
        }

        btncancel.setOnClickListener {

        }

    }

    fun addLaundryShop(){
        val shopName = edtShopName.text.toString()
        val shopAddress = edtAddressShop.text.toString()
        val shopContactPerson = edtContactPerson.text.toString()
        val PhoneNo = edtPhoneNo.text.toString()
        val phoneNoDigit = lblphone_no.text.toString()
        val dateEstablish = lblshop_date_establish.text.toString()
        val GetPhoneNo = "$phoneNoDigit$PhoneNo"

        if(shopName == ""){

        }else if (shopAddress == ""){

        }else if (shopContactPerson == ""){

        }else if (PhoneNo == "" || PhoneNo.length > 9){

        }else {
//            handler.insertLaundryShop(laundry_shop = Laundry_Shop(0,shopName,shopAddress,dateEstablish,"Active",shopContactPerson,GetPhoneNo))
//            Toast.makeText(this,"Laundry shop has been added successfully.", Toast.LENGTH_LONG).show()
        }

        edtShopName.setText("")
        edtAddressShop.setText("")
        edtContactPerson.setText("")
        edtPhoneNo.setText("")
    }


}