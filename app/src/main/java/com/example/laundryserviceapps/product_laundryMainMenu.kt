package com.example.laundryserviceapps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.product_main_menu_page.*

class product_laundryMainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_main_menu_page)
        val prf = getSharedPreferences("retailer_user_details", MODE_PRIVATE)
        textViewUsername.text =  prf.getString("username", null)
        btnAddShop.setOnClickListener()
        {
            val i = Intent(this@product_laundryMainMenu, product_retailRegistration::class.java)
            startActivity(i)

        }

        btnViewShop.setOnClickListener()
        {
            val i = Intent(this@product_laundryMainMenu, product_viewShopItem::class.java)
            startActivity(i)
        }
        btnAddPromo.setOnClickListener()
        {
            val i = Intent(this@product_laundryMainMenu, AddPromotion::class.java)
            startActivity(i)
        }
        btnUpdateOrder.setOnClickListener()
        {
            val i = Intent(this@product_laundryMainMenu, UpdateOrderStatus::class.java)
            startActivity(i)
        }
        btnLogout.setOnClickListener()
        {
            val editor=prf.edit()
            editor.clear()
            editor.apply()
            val i = Intent(this@product_laundryMainMenu, retailer_login::class.java)
            startActivity(i)
        }


    }

}