package com.example.laundryserviceapps

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.laundryserviceapps.ClassModel.Promotion
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_add_promotion.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_select_time_slot.*
import java.util.*

class AddPromotion : AppCompatActivity() {

    lateinit var handler: SQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_promotion)

        handler = SQLiteHelper(this)

        btnsavepromotion.setOnClickListener {
            add_promotion()
        }

    }

    fun add_promotion(){
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        lbl_promo_expired_date.setOnClickListener {
            val date_pickup = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                //Display Selected date in TextView
                lbl_pickup_date.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
                lbl_get_date_pickup.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
            },mYear,mMonth,mDay)
            date_pickup.datePicker.minDate = c.getTimeInMillis()
            date_pickup.show()
        }

//        if (confirmpassword.isEmpty()){
//            edtConfirmpassword.error = "The compulsory fields cannot be empty, please enter the compulsory field."
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            edtEmail.error = "Please enter the valid email."
//        } else{
//            handler.addPromotion(promotion = Promotion(0,"Laundry Service Off RM 2.0",2.0,"21-7-2020"))
//            Toast.makeText(this,"User account has been registered.", Toast.LENGTH_LONG).show()
//            finish()
//        }


    }
}