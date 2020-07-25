package com.example.laundryserviceapps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.feedback_page.*
import kotlinx.android.synthetic.main.feedback_page.view.*


class feedback: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback_page)
        btnRate.setOnClickListener{
            validate()
//            Toast.makeText(this@feedback,"Please rate", Toast.LENGTH_SHORT).show()
        }
    }
    fun validate(){
        if(ratingBar.rating <1)
        {
            Toast.makeText(this@feedback,"Please rate", Toast.LENGTH_SHORT).show()
        }
    }
}