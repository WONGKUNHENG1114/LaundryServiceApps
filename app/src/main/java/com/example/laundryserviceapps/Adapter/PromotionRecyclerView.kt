package com.example.laundryserviceapps.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.ClassModel.Promotion
import com.example.laundryserviceapps.MainActivity
import com.example.laundryserviceapps.R
import com.example.laundryserviceapps.SelectService
import com.example.laundryserviceapps.Test
import kotlinx.android.synthetic.main.list_laundryshop_row.view.*
import kotlinx.android.synthetic.main.list_of_promotion.view.*

class PromotionRecyclerView(val context: Context, val lstPromotion:List<Promotion>):
    RecyclerView.Adapter<PromotionRecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(promotion: Promotion){
            itemView.promotionname.text = promotion.promo_name
            itemView.discount.text = promotion.discount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_of_promotion,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lstPromotion.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(lstPromotion[position])

        holder.itemView.setOnClickListener {
            val promo = lstPromotion.get(position)
            var g_promo : String = promo.promo_name
            var g_discount : String = promo.discount.toString()
            val intent = Intent(context, Test::class.java)
            intent.putExtra("promo_name",g_promo)
            intent.putExtra("discount",g_discount)
            context.startActivity(intent)
        }
    }
}