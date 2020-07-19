package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome_laundry_app.*

class WelcomeLaundryApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_laundry_app)

        btnUserMode.setOnClickListener {
            val intent = Intent(this,LoginPage::class.java)
            startActivity(intent)
        }

        btnAdminMode.setOnClickListener {
            val intent = Intent(this,StaffLoginPage::class.java)
            startActivity(intent)
        }
    }
}