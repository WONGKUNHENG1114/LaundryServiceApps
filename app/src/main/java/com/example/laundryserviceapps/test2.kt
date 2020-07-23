package com.example.laundryserviceapps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.test2.*

class test2: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test2)

        button2.setOnClickListener {
            val intent = Intent(this,test3::class.java)
            startActivity(intent)
        }

    }
}