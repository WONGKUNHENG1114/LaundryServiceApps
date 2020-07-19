package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*
import java.lang.String
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.time.days


class Test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var intent = intent
        val get_shop = intent.getStringExtra("promo_name")
        val get_address = intent.getStringExtra("discount")

        getshopname.text = get_shop
        getaddress.text = get_address

//        val dateTime = LocalDate.now()
//        val start = LocalDate.of(2020,7,18)
//        val end = LocalDate.of(2020,7,20)
//
//        val period = Period.between(start, end)
//        val days = ChronoUnit.DAYS.between(start,end)
//
//        if(dateTime.isEqual(end)){
//            lbldays.text = days.toString()
//        }else{
//            lbldays.text = "expired"
//        }


//        lbltimepicker.setOnClickListener {
//            val now = Calendar.getInstance()
//            val timepicker = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
//                val selected_time = Calendar.getInstance()
//                selected_time.set(Calendar.HOUR_OF_DAY, hourOfDay)
//                selected_time.set(Calendar.MINUTE, minute)
//                //displaytime.text = time_format.format(selected_time.time)
//                if (selected_time.getTimeInMillis() >= now.getTimeInMillis()) {
//                    //it's after current
//                    val hour = hourOfDay % 12
//                    displaytime.setText(String.format("%02d:%02d %s",
//                        if (hour == 0) 12
//                        else hour, minute,
//                        if (hourOfDay < 12) "AM"
//                        else "PM"))
//                    lbltimepicker.setText(String.format("%02d:%02d %s",
//                        if (hour == 0) 12 else hour, minute, if (hourOfDay < 12) "AM" else "PM"))
//
//                } else {
//                    Toast.makeText(applicationContext, "Invalid pick up time slot selected", Toast.LENGTH_LONG).show()
//                }
//
//            },
//                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
//            timepicker.show()
//        }



    }
}