package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryserviceapps.Adapter.PromotionRecyclerView
import com.example.laundryserviceapps.Adapter.RecyclerViewHolder
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.ClassModel.Promotion
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_my_order_list.*
import kotlinx.android.synthetic.main.activity_promotion_page.*
import kotlinx.android.synthetic.main.activity_review_order.*

class PromotionPage : AppCompatActivity() {

    private var mlstpromo = ArrayList<Promotion>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion_page)
        create_promotion()

        imgback11.setOnClickListener {
            onBackPressed()
        }
    }

    fun create_promotion(){
        try{
            val dbhandler = SQLiteHelper(this)
            dbhandler.onCreatePromotion()

            val lstpromo = dbhandler.getPromo()
            val adapter=PromotionRecyclerView(this,lstpromo)
            rvpromotion.adapter=adapter
            rvpromotion.layoutManager= LinearLayoutManager(this)

        } catch (e: Exception) {
            Log.i("error", e.toString())
        }
    }
}