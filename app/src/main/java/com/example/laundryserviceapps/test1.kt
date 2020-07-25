package com.example.laundryserviceapps

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.test1.*
import java.util.*

class test1: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test1)
        val curent :Date=Calendar.getInstance().time
        editTextTextPersonName2.setText(curent.toString())
    }

}