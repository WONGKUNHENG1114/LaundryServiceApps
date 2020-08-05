package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.laundryserviceapps.DatabaseHandler.retailer_databaseHandler
import kotlinx.android.synthetic.main.activity_welcome_laundry_app.*

class WelcomeLaundryApp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_laundry_app)

        btnUserMode.setOnClickListener {
            val intent = Intent(this,LoginPage::class.java)
            startActivity(intent)
        }

        btnRetailerMode.setOnClickListener {
            val intent = Intent(this,retailer_login::class.java)
            startActivity(intent)
        }
    }
}