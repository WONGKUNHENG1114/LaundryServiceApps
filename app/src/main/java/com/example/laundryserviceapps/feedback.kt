package com.example.laundryserviceapps

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryserviceapps.DatabaseHandler.product_databaseHandler
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_add_laundry_shop__page.*

import kotlinx.android.synthetic.main.feedback_page.*
import kotlinx.android.synthetic.main.feedback_page.textViewContactPerson


class feedback: AppCompatActivity() {
    private  var shopName=""
    private var shopAddress=""
    private lateinit var shopImage:ByteArray
    private var contactPerson=""
    private lateinit var adapter:product_FeedbackAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback_page)


        val i=intent
        shopName= i.getStringExtra("shopName")
        val LaundryDB=product_databaseHandler(this)
        LaundryDB.readableDatabase
        val laundryArr= LaundryDB.viewDetailShopLaundry(shopName)
        val laundryData=laundryArr[0]
        textViewLaundryFeedbackShopName.text = laundryData.shopName
        textViewFeedbackAddress.text=laundryData.shopAddress
        textViewContactPerson.text=laundryData.contactPerson
        val imageBitmap=laundryData.getImage()
        imageViewFeedbackLaundry.setImageBitmap(imageBitmap)
//        val bitmapImage=getImage()
//        textViewContactPerson.text=contactPerson
//        imageViewFeedbackLaundry.setImageBitmap(bitmapImage)
        retrieveData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun retrieveData()
    {
        val db=
            SQLiteHelper(
                this
            )
        val  registeredRetailer=db.getFeedback(shopName)
        adapter=product_FeedbackAdapter(this,registeredRetailer)

        rvComment.adapter=adapter
        rvComment.layoutManager= LinearLayoutManager(this)
    }
    fun getImage(): Bitmap {
        val data=shopImage
        return BitmapFactory.decodeByteArray(data, 0, data!!.size)
    }
}