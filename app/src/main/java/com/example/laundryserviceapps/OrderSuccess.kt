package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_order_success.*
import kotlinx.android.synthetic.main.activity_review_order.*

class OrderSuccess : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        val i = intent
        val get_shop_name = i.getStringExtra("Shop_name_feedback")
        val get_shop_address = i.getStringExtra("Shop_address_feedback")

        lblshop_order_success.setText(get_shop_name)
        lblshop_address_order_success.setText(get_shop_address)


        btndone.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnreviewfeedback.setOnClickListener {
            val intent = Intent(this,ReviewFeedback::class.java)
            intent.putExtra("Shop_name_feedback1",lblshop_order_success.text.toString())
            startActivity(intent)
            finish()
        }
    }
}