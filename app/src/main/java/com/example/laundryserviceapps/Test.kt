package com.example.laundryserviceapps

import android.app.ActionBar
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*
import java.lang.String
import java.util.*


class Test : AppCompatActivity() {
    //var time_format = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        var intent = intent
        val get_shop = intent.getStringExtra("Shop")
        val get_address = intent.getStringExtra("Address")

        getshopname.text = get_shop
        getaddress.text = get_address

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