package com.example.laundryserviceapps

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.activity_select_time_slot.*
import java.text.SimpleDateFormat
import java.util.*

class SelectTimeSlot : AppCompatActivity() {
    var time_format = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_time_slot)

        val i = intent
        val get_laundry_service_p3 = i.getStringExtra("P3LAUNDRY_TYPE")
        val get_payment_amt_p3 = i.getStringExtra("P3PAYMENTAMT")
        val get_address_pickup_p2 = i.getStringExtra("P3PICKUP_ADDRESS")
        val get_list_item_selection_p3 = i.getStringExtra("P3LISTITEM")

        lbl_get_laundry_service_p_4.setText(get_laundry_service_p3)
        lbl_get_payment_amt_p_4.setText(get_payment_amt_p3)
        lbl_get_address_pickup_p_3.setText(get_address_pickup_p2)
        lbl_get_list_item3.setText(get_list_item_selection_p3)

//        val get_address_delivery_p3 = i.getStringExtra("P3DELIVERY_ADDRESS")
//        val get_city_delivery_p3 = i.getStringExtra("P3DELIVERY_CITY")
//        val get_poscode_delivery_p3 = i.getStringExtra("P3DELIVERY_POSCODE")
//        val get_state_delivery_p3 = i.getStringExtra("P3DELIVERY_STATE")
//        val get_full_delivery_address = "$get_address_delivery_p3, $get_city_delivery_p3, $get_poscode_delivery_p3, $get_state_delivery_p3"

        val get_deliveryaddress = i.getStringExtra("DELIVERYADDRESS")
        val get_shopname_3 = i.getStringExtra("Shop3")
        val get_shopaddress_3 = i.getStringExtra("Address3")

        lbl_getshopname3.setText(get_shopname_3)
        lbl_getshopaddress3.setText(get_shopaddress_3)
        lbl_get_address_delivery_p_3.setText(get_deliveryaddress)

        val get_promo3 = i.getStringExtra("PROMOTION_NAME2")
        val get_discount3 = i.getStringExtra("PROMOTION_PRICE2")

        lblget_promo3.setText(get_promo3)
        lbl_get_discount3.setText(get_discount3)

        imgback.setOnClickListener {
            onBackPressed()
        }

        date_selection()
        time_selection()


        btnproceed.setOnClickListener {
            proceed_to_next()
        }
    }

    fun date_selection(){
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        lbl_pickup_date.setOnClickListener {
            val date_pickup = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                //Display Selected date in TextView
                lbl_pickup_date.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
                lbl_get_date_pickup.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
            },mYear,mMonth,mDay)
            date_pickup.datePicker.minDate = c.getTimeInMillis()
            date_pickup.show()
        }

        lbl_delivery_date.setOnClickListener {
            val date_delivery = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                //Display Selected date in TextView
                lbl_delivery_date.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
                lbl_get_date_delivery.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
            },mYear,mMonth,mDay)
            date_delivery.datePicker.minDate = c.getTimeInMillis()
            date_delivery.show()
        }

    }



    fun time_selection(){
            lbl_pickup_time_slot_available.setOnClickListener {
                val now = Calendar.getInstance()
                val time_pickup = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val selected_time = Calendar.getInstance()
                    selected_time.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selected_time.set(Calendar.MINUTE, minute)

                    //displaytime.text = time_format.format(selected_time.time)
                    if (selected_time.getTimeInMillis() >= now.getTimeInMillis()) {
                        //it's after current
                        val hour = hourOfDay % 12
                        lbl_pickup_timeslot.setText(String.format("%02d:%02d %s",
                            if (hour == 0) 12 else hour, minute,
                            if (hourOfDay < 12) "AM" else "PM"))
                        lbl_pickup_time_slot_available.setText(String.format("%02d:%02d %s",
                            if (hour == 0) 12 else hour, minute,
                            if (hourOfDay < 12) "AM" else "PM"))

                    } else {
                        Toast.makeText(applicationContext, "Invalid pick up time slot selected", Toast.LENGTH_LONG).show()
                    }
                },
                    now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                time_pickup.show()
            }

            val pickup_date_slot = lbl_pickup_date.text.toString()
            val delivery_date_slot = lbl_delivery_date.text.toString()

            lbl_delivery_time_slot_available.setOnClickListener {
                val now = Calendar.getInstance()
                val time_delivery = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val selected_delivery_time = Calendar.getInstance()
                    selected_delivery_time.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selected_delivery_time.set(Calendar.MINUTE, minute)

                    if(pickup_date_slot.equals(delivery_date_slot)){
                        if (selected_delivery_time.getTimeInMillis() >= now.getTimeInMillis()) {
                            val hour = hourOfDay % 12
                            lbl_delivery_timeslot.setText(String.format("%02d:%02d %s",
                                if (hour == 0) 12 else hour, minute,
                                if (hourOfDay < 12) "AM" else "PM"))
                            lbl_delivery_time_slot_available.setText(String.format("%02d:%02d %s",
                                if (hour == 0) 12 else hour, minute,
                                if (hourOfDay < 12) "AM" else "PM"))

                        } else {
                            Toast.makeText(applicationContext, "Invalid pick up time slot selected", Toast.LENGTH_LONG).show()
                        }
                    }else {
                        lbl_delivery_timeslot.text = time_format.format(selected_delivery_time.time)
                        lbl_delivery_time_slot_available.text = time_format.format(selected_delivery_time.time)
                    }
                },
                    now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                time_delivery.show()
            }
    }


    fun proceed_to_next(){
        val pickup_time_slot = lbl_pickup_timeslot.text.toString()
        val delivery_time_slot = lbl_delivery_timeslot.text.toString()

        if(lbl_get_date_pickup.text == "" ) {
            Toast.makeText(this,"Please select the date for pickup.", Toast.LENGTH_LONG).show()
        } else if(lbl_get_date_delivery.text == "" ) {
            Toast.makeText(this,"Please select the date for delivery.", Toast.LENGTH_LONG).show()
        } else if (pickup_time_slot.equals(delivery_time_slot)) {
            Toast.makeText(applicationContext, "Invalid time slot selected", Toast.LENGTH_LONG).show()
        } else if (radioGroup2.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please select the payment method", Toast.LENGTH_LONG).show()
        } else if(!checkBoxTerm.isChecked) {
            Toast.makeText(this, "Please check this term of condition and privacy notice", Toast.LENGTH_LONG).show()
        } else {
            pass_the_value()
        }
//        else if(spn_time_pickup.selectedItemPosition != -1 && spn_time_delivery.selectedItemPosition != -1 && lbl_get_date_delivery.text != ""
//            && lbl_get_date_delivery.text != "" && radioGroup2.getCheckedRadioButtonId() != -1 && checkBoxTerm.isChecked){

//        }

    }

    fun pass_the_value(){
            val intent = Intent(this, ReviewOrder::class.java)
            intent.putExtra("P4LAUNDRY_TYPE",lbl_get_laundry_service_p_4.text.toString())
            intent.putExtra("P4PAYMENT_AMT", lbl_get_payment_amt_p_4.text.toString())
            intent.putExtra("P4PICKUP_ADDRESS", lbl_get_address_pickup_p_3.text.toString())
            intent.putExtra("P4DELIVERY_ADDRESS", lbl_get_address_delivery_p_3.text.toString())
            intent.putExtra("P4LISTITEM",lbl_get_list_item3.text.toString())

//            if(radoption1.isChecked){
                intent.putExtra("PAYMENTMETHOD",radoption1.text.toString())
//            }else{
//                intent.putExtra("PAYMENTMETHOD",radoption2.text.toString())
//            }

            intent.putExtra("PROMOTION_NAME3", lblget_promo3.text.toString())
            intent.putExtra("PROMOTION_PRICE3", lbl_get_discount3.text.toString())

            intent.putExtra("PICKUP_TIME", lbl_pickup_timeslot.text.toString())
            intent.putExtra("DELIVERY_TIME", lbl_delivery_timeslot.text.toString())
            intent.putExtra("PICKUP_DATE", lbl_pickup_date.text.toString())
            intent.putExtra("DELIVERY_DATE", lbl_delivery_date.text.toString())

            intent.putExtra("FShop4", lbl_getshopname3.text.toString())
            intent.putExtra("FAddress4", lbl_getshopaddress3.text.toString())

            startActivity(intent)
    }

//    fun validate_time_frame(){
//        val timepickup = spn_time_pickup.selectedItemPosition
//        val timedelivery = spn_time_delivery.selectedItemPosition
//
//        if(lbl_pickup_date.text.equals(lbl_delivery_date.text)) {
//
//        }
//    }
}
