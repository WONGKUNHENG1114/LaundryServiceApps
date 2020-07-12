package com.example.laundryserviceapps

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_time_slot.*
import java.util.*

class SelectTimeSlot : AppCompatActivity() {

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

        val get_address_delivery_p3 = i.getStringExtra("P3DELIVERY_ADDRESS")
        val get_city_delivery_p3 = i.getStringExtra("P3DELIVERY_CITY")
        val get_poscode_delivery_p3 = i.getStringExtra("P3DELIVERY_POSCODE")
        val get_state_delivery_p3 = i.getStringExtra("P3DELIVERY_STATE")
        val get_full_delivery_address = "$get_address_delivery_p3, $get_city_delivery_p3, $get_poscode_delivery_p3, $get_state_delivery_p3"

        lbl_get_address_delivery_p_3.setText(get_full_delivery_address)

        imgback.setOnClickListener {
            onBackPressed()
        }

        timeslot_selection()
        date_selection()

        btnproceed.setOnClickListener {
            proceed_to_next()
        }
    }



    fun timeslot_selection(){
        spn_time_pickup?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spn_time_pickup.selectedItemPosition){
                    1 -> lbl_pickup_timeslot.text = "12:00PM  - 2:00PM"
                    2 -> lbl_pickup_timeslot.text = "2:00PM - 4:00PM"
                    3 -> lbl_pickup_timeslot.text = "4:00PM - 6:00PM"
                    4 -> lbl_pickup_timeslot.text = "6:00PM - 8:00PM"
                    5 -> lbl_pickup_timeslot.text = "8:00PM - 10:00PM"
                    6 -> lbl_pickup_timeslot.text = "10:00PM - 12:00AM"
                }
            }
        }

        spn_time_delivery?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spn_time_delivery.selectedItemPosition){
                    1 -> lbl_delivery_timeslot.text = "12:00PM  - 2:00PM"
                    2 -> lbl_delivery_timeslot.text = "2:00PM - 4:00PM"
                    3 -> lbl_delivery_timeslot.text = "4:00PM - 6:00PM"
                    4 -> lbl_delivery_timeslot.text = "6:00PM - 8:00PM"
                    5 -> lbl_delivery_timeslot.text = "8:00PM - 10:00PM"
                    6 -> lbl_delivery_timeslot.text = "10:00PM - 12:00AM"
                }
            }
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
            date_pickup.show()
        }

        lbl_delivery_date.setOnClickListener {
            val date_delivery = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                //Display Selected date in TextView
                lbl_delivery_date.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
                lbl_get_date_delivery.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
            },mYear,mMonth,mDay)
            date_delivery.show()
        }
    }

    fun proceed_to_next(){
        val timepickup = spn_time_pickup.selectedItemPosition
        val timedelivery = spn_time_delivery.selectedItemPosition

        if(lbl_pickup_date.text.equals(lbl_delivery_date.text)) {
            if(timepickup == 1 && timedelivery == 1) {
                Toast.makeText(this,"Please select the valid time slot or date for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 2 && timedelivery <= 2) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 3 && timedelivery <= 3) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 4 && timedelivery <= 4) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 5 && timedelivery <= 5) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 6 && timedelivery <= 6) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 7 && timedelivery <= 7) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 8 && timedelivery <= 8) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 9 && timedelivery <= 9) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 10 && timedelivery <= 10) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 11 && timedelivery <= 11) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }else if(timepickup == 12 && timedelivery <= 12) {
                Toast.makeText(this,"Please select the valid time slot for delivery.", Toast.LENGTH_LONG).show()
            }

            else if (timepickup >= 1 && timepickup <= 3 && timedelivery >= 1 && timedelivery <= 3) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 2 && timepickup <= 4 && timedelivery >= 2 && timedelivery <= 4) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 3 && timepickup <= 5 && timedelivery >= 3 && timedelivery <= 5) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 4 && timepickup <= 6 && timedelivery >= 4 && timedelivery <= 6) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 5 && timepickup <= 7 && timedelivery >= 5 && timedelivery <= 7) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            }
            else if (timepickup >= 6 && timepickup <= 8 && timedelivery >= 6 && timedelivery <= 8) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 7 && timepickup <= 9 && timedelivery >= 7 && timedelivery <= 9) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 8 && timepickup <= 10 && timedelivery >= 8 && timedelivery <= 10) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 9 && timepickup <= 11 && timedelivery >= 9 && timedelivery <= 11) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else if (timepickup >= 10 && timepickup <= 12 && timedelivery >= 10 && timedelivery <= 12) {
                Toast.makeText(this, "Please select the valid time slot", Toast.LENGTH_LONG).show()
            } else {
                pass_the_value()
            }
//            if (spn_time_pickup.selectedItemPosition >= 2 && spn_time_delivery.selectedItemPosition == 2) {
//                Toast.makeText(this,"Please select the valid time slot for pickup.",Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 3 && spn_time_delivery.selectedItemPosition == 3) {
//                Toast.makeText(this,"Please select the valid time slot for pickup.",Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 4 && spn_time_delivery.selectedItemPosition == 4) {
//                Toast.makeText(this,"Please select the valid time slot for pickup.",Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 5 && spn_time_delivery.selectedItemPosition == 5) {
//                Toast.makeText(this,"Please select the valid time slot for pickup.",Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 6 && spn_time_delivery.selectedItemPosition == 6) {
//                Toast.makeText(this, "Please select the valid time slot for pickup.", Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 7 && spn_time_delivery.selectedItemPosition == 7) {
//                Toast.makeText(this, "Please select the valid time slot for pickup.", Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 8 && spn_time_delivery.selectedItemPosition == 8) {
//                Toast.makeText(this, "Please select the valid time slot for pickup.", Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 9 && spn_time_delivery.selectedItemPosition == 9) {
//                Toast.makeText(this, "Please select the valid time slot for pickup.", Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 10 && spn_time_delivery.selectedItemPosition == 10) {
//                Toast.makeText(this, "Please select the valid time slot for pickup.", Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 11 && spn_time_delivery.selectedItemPosition == 11) {
//                Toast.makeText(this, "Please select the valid time slot for pickup.", Toast.LENGTH_LONG).show()
//            }else if (spn_time_pickup.selectedItemPosition >= 12 && spn_time_delivery.selectedItemPosition == 12) {
//                Toast.makeText(this, "Please select the valid time slot for pickup.", Toast.LENGTH_LONG).show()
//            }
//            validate_time_frame()
        } else if(timepickup == 0) {
            Toast.makeText(this,"Please select the time slot for pick up.", Toast.LENGTH_LONG).show()
        } else if(lbl_get_date_pickup.text == "" ) {
            Toast.makeText(this,"Please select the date for pickup.", Toast.LENGTH_LONG).show()
        } else if(timedelivery == 0) {
            Toast.makeText(this,"Please select the time slot for delivery.", Toast.LENGTH_LONG).show()
        } else if(lbl_get_date_delivery.text == "" ) {
            Toast.makeText(this,"Please select the date for delivery.", Toast.LENGTH_LONG).show()
        }
        if (radioGroup2.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please select the payment method", Toast.LENGTH_LONG).show()
        } else if(!checkBoxTerm.isChecked) {
            Toast.makeText(this, "Please check this term of condition and privacy notice", Toast.LENGTH_LONG).show()
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

            if(radoption1.isChecked){
                intent.putExtra("PAYMENTMETHOD",radoption1.text.toString())
            }else{
                intent.putExtra("PAYMENTMETHOD",radoption2.text.toString())
            }

            intent.putExtra("PICKUP_TIME", lbl_pickup_timeslot.text.toString())
            intent.putExtra("DELIVERY_TIME", lbl_delivery_timeslot.text.toString())
            intent.putExtra("PICKUP_DATE", lbl_pickup_date.text.toString())
            intent.putExtra("DELIVERY_DATE", lbl_delivery_date.text.toString())
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
