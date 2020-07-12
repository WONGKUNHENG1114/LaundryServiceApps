package com.example.laundryserviceapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryserviceapps.Adapter.OrderHolder
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_my_order_list.*

class History : AppCompatActivity() {

    lateinit var handler: SQLiteHelper
    private var historyList = ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        handler = SQLiteHelper(this)
        historyList= handler?.getOrderList()!!

        imgback5.setOnClickListener {
            finish()
        }

        btnback2.setOnClickListener {
            finish()
        }

        view_order_history_list()
    }

    private fun view_order_history_list() {
        handler = SQLiteHelper(this)
        historyList= handler?.getOrderHistoryList()!!

        val adapter = OrderHolder(historyList)
        rvhistorylist.layoutManager = LinearLayoutManager(this)
        rvhistorylist.adapter = adapter
    }

    override fun onResume() {
        view_order_history_list()
        super.onResume()

    }
}