package com.example.laundryserviceapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.R

class RecyclerViewHolder(val orders:ArrayList<Order>): RecyclerView.Adapter<RecyclerViewHolder.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val lblorder_no  = itemView.findViewById(R.id.lblorder_no) as TextView
        val lblorder_status  = itemView.findViewById(R.id.lblorder_status) as TextView
        val lblorder_date  = itemView.findViewById(R.id.lblorder_date) as TextView
        val lblgrand_payment_amt  = itemView.findViewById(R.id.lblgrand_payment_amt) as TextView
        val relative = itemView.findViewById(R.id.relative) as RelativeLayout
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewHolder.ViewHolder {
        val v: View = LayoutInflater.from(p0.context).inflate(R.layout.my_order_list,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return orders.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val orders : Order = orders[p1]
        p0.lblorder_no.text = orders.order_no.toString()
        p0.lblorder_date.text = orders.order_date
        p0.lblorder_status.text = orders.order_status
        p0.lblgrand_payment_amt.text = orders.payment_amt.toString()

//        p0.relative.setOnClickListener {
//            val intent = Intent(mctx, DemoRecyclerview::class.java)
//            intent.putExtra("ORDER_NO", )
//            intent.putExtra("ORDER_DATE",orders.order_date)
//            intent.putExtra("PAYMENT_AMT",orders.payment_amt)
//            mctx.startActivity(intent)
//        }
    }
}