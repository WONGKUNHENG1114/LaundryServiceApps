package com.example.laundryserviceapps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_test.*

class Test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var intent = intent
        val get_shop = intent.getStringExtra("Shop")
        val get_address = intent.getStringExtra("Address")
        val get_promo = intent.getStringExtra("promo_name")
        val get_discount = intent.getStringExtra("discount")

        getshopname.setText(get_shop)
        getaddress.setText(get_address)

        getpromo.setText(get_promo)
        getdiscount.setText(get_discount)

        linkpromo.setOnClickListener {
            val intent = Intent(this,SelectService::class.java)
            startActivity(intent)
        }

        btnok.setOnClickListener {
            val intent = Intent(this, SelectService::class.java)
            intent.putExtra("discount2",getdiscount.text.toString())
            startActivity(intent)
        }


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