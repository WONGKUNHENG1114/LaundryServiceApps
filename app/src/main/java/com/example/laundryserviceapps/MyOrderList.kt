package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.LinearLayout.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.Adapter.RecyclerViewHolder
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_my_order_list.*

class MyOrderList : AppCompatActivity() {

    lateinit var handler: SQLiteHelper
    private var order_list = ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order_list)

        handler = SQLiteHelper(this)
        order_list= handler?.getOrderList()!!

        imgback3.setOnClickListener {
            finish()
        }

        btnback.setOnClickListener {
            finish()
        }

        view_order_list()
    }

    private fun view_order_list(){
//        val res =  handler.getallOrderList
//        val buffer = StringBuffer()
//        if(res != null && res.count>0) {
//            while (res.moveToNext()) {
//                buffer.append("Order No: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + res.getString(0) + "\n")
//                buffer.append("Order Date: \t\t\t" + res.getString(1) + "\n")
//                buffer.append("Order Status: \t\t\t\t\t\t\t\t\t\t\t\t" + res.getString(2) + "\n")
//                buffer.append("Payment Amount (RM): \t\tRM " + res.getString(3)+ "\n")
//                buffer.append("------------------------------------------------------------------------\n\n")
//            }
//            lblresult.text = buffer.toString()
//        }else{
//            Toast.makeText(this, "No order records .... ", Toast.LENGTH_SHORT).show()
//        }

        handler = SQLiteHelper(this)
        order_list= handler?.getOrderList()!!

        val adapter = RecyclerViewHolder(order_list)
        recyclervieworderlist.layoutManager = LinearLayoutManager(this)
        recyclervieworderlist.adapter = adapter
    }

    override fun onResume() {
        view_order_list()
        super.onResume()
    }
}