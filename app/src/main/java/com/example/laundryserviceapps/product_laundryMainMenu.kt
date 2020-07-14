package com.example.laundryserviceapps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.product_main_menu_page.*

class product_laundryMainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_main_menu_page)

        btnAddShop.setOnClickListener()
        {
            val i= Intent(this@product_laundryMainMenu,product_retailRegistration::class.java)
            startActivity(i)
        }

        btnViewShop.setOnClickListener()
        {
            val i= Intent(this@product_laundryMainMenu,product_viewShop::class.java)
            startActivity(i)
        }

        img_update.setOnClickListener {
            val i= Intent(this@product_laundryMainMenu,UpdateOrderStatus::class.java)
            startActivity(i)
        }

    }

}