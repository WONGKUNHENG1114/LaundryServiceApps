package com.example.laundryserviceapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryserviceapps.Adapter.LaundryShopSelectionRecyclerView
import com.example.laundryserviceapps.Adapter.LaundryShopStatusList
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_laundry_shop_selection_list.*
import kotlinx.android.synthetic.main.activity_update_laundry_shop_status.*
import kotlinx.android.synthetic.main.activity_update_order_status.*

class UpdateLaundryShopStatus : AppCompatActivity() {

    lateinit var handler: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_laundry_shop_status)

        handler = SQLiteHelper(this)
        view_laundry_shop()

        btnupdate_business_status.setOnClickListener {
            update_laundry_shop()
            view_laundry_shop()
        }

    }

    fun view_laundry_shop(){
        try{
            val dbhandler = SQLiteHelper(this)
            val lstLS = dbhandler.getLaundryShop()
            val adapter= LaundryShopStatusList(lstLS)
            rvlaundryShopStatus.adapter = adapter
            rvlaundryShopStatus.layoutManager = LinearLayoutManager(this)
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.i("error", e.toString())
        }
    }

    fun update_laundry_shop(){
        val laundry_shop_ID = edtLaundryShopID.text.toString()
        val business_status = spn_laundryshop_status.selectedItem.toString()
//
        if (laundry_shop_ID.isEmpty()) {
            edtLaundryShopID.error = "Order number cannot be empty, please enter the order number."
        }
        if (spn_laundryshop_status.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select the business status if you want to update business status.", Toast.LENGTH_LONG)
                .show()
        } else {
            val laundryshop_status = Laundry_Shop()
            laundryshop_status.shopID = Integer.parseInt(laundry_shop_ID)
            laundryshop_status.shop_status = business_status

            handler.updateLaundryShopStatus(laundryshop_status)
            Toast.makeText(this,"Business status has been updated ....", Toast.LENGTH_LONG).show()

            edtLaundryShopID.setText("")
            spn_laundryshop_status.setSelection(0)
        }
    }

    override fun onResume() {
        view_laundry_shop()
        super.onResume()
    }
}