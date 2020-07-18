package com.example.laundryserviceapps.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.R
import com.example.laundryserviceapps.Test
import kotlinx.android.synthetic.main.list_laundryshop_row.view.*


class LaundryShopRecycler(val lstLaundryshop:ArrayList<Laundry_Shop>, val context: Context):
    RecyclerView.Adapter<LaundryShopRecycler.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(laundryshop: Laundry_Shop){
            itemView.laundryshop.text = laundryshop.laundry_shop_name
            itemView.laundryshopaddress.text = laundryshop.laundry_shop_Address
//            itemView.shopstatus.text = laundryshop.shop_status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_laundryshop_row,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lstLaundryshop.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(lstLaundryshop[position])

        holder.itemView.setOnClickListener {
            val LS = lstLaundryshop.get(position)
            var g_shop : String = LS.laundry_shop_name
            var g_address : String = LS.laundry_shop_Address

            if(LS.shop_status != "Active"){
                Toast.makeText(context,"This laundry shop status is not active.", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(context, Test::class.java)
                intent.putExtra("Shop",g_shop)
                intent.putExtra("Address",g_address)
                context.startActivity(intent)
            }
        }
    }

}