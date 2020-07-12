package com.example.laundryserviceapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.R

class OrderHolder(val orderslist:ArrayList<Order>): RecyclerView.Adapter<OrderHolder.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lbl_orderno = itemView.findViewById(R.id.lbl_orderno) as TextView
        val lbl_orderdate = itemView.findViewById(R.id.lbl_orderdate) as TextView
        val lbl_grandpaymentamt = itemView.findViewById(R.id.lbl_grandpaymentamt) as TextView

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderHolder.ViewHolder {
        val v: View = LayoutInflater.from(p0.context).inflate(R.layout.history_layout, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return orderslist.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val orders: Order = orderslist[p1]
        p0.lbl_orderno.text = orders.order_no.toString()
        p0.lbl_orderdate.text = orders.order_date
        p0.lbl_grandpaymentamt.text = orders.payment_amt.toString()
    }
}