package com.example.laundryserviceapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryserviceapps.Adapter.RecyclerViewHolder
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_update_order_status.*

class UpdateOrderStatus : AppCompatActivity() {

    lateinit var handler: SQLiteHelper
    private var display_order_list = ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_order_status)

        handler = SQLiteHelper(this)
        display_order_list= handler.getOrderList()

        view_update_order_list()

        btnupdate_status.setOnClickListener {
            validate_order_status()
            view_update_order_list()
        }

    }

    private fun view_update_order_list(){
        handler = SQLiteHelper(this)
        display_order_list= handler.getOrderList()

        val adapter = RecyclerViewHolder(display_order_list)
        rv_update_order_status.layoutManager = LinearLayoutManager(this)
        rv_update_order_status.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun validate_order_status() {
        val order_number = editTextNumber.text.toString()
        val order_status = spn_order_status.selectedItem.toString()

        if (order_number.isEmpty()) {
            editTextNumber.error = "Order number cannot be empty, please enter the order number."
        }
        if (spn_order_status.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select the order status if you want to update order status.", Toast.LENGTH_LONG)
                .show()
        } else {
            val display_order_list = Order()
            display_order_list.order_no = Integer.parseInt(order_number)
            display_order_list.order_date = ""
            display_order_list.order_status = order_status
            display_order_list.payment_amt = 0.0

            handler.updateOrderStatus(display_order_list)
            Toast.makeText(this,"Order status has been updated ....",Toast.LENGTH_LONG).show()

            editTextNumber.text = null
            spn_order_status.setSelection(0)
        }

    }

    override fun onResume() {
        view_update_order_list()
        super.onResume()
    }
}