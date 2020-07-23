package com.example.laundryserviceapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.R

class LaundryShopStatusList(val lstlaundryshop:List<Laundry_Shop>): RecyclerView.Adapter<LaundryShopStatusList.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblshop_ID = itemView.findViewById(R.id.lblshop_ID) as TextView
        val lblshop_name = itemView.findViewById(R.id.lblshop_name) as TextView
        val lbl_laundryshop_address = itemView.findViewById(R.id.lbl_laundryshop_address) as TextView
        val lbl_shop_status = itemView.findViewById(R.id.lbl_shop_status) as TextView
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v: View = LayoutInflater.from(p0.context).inflate(R.layout.list_laundryshop_status, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lstlaundryshop.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val laundryshop: Laundry_Shop = lstlaundryshop[p1]
        p0.lblshop_ID.text = laundryshop.shopID.toString()
        p0.lblshop_name.text = laundryshop.laundry_shop_name
        p0.lbl_laundryshop_address.text = laundryshop.laundry_shop_Address
        p0.lbl_shop_status.text = laundryshop.shop_status
    }
}