package com.example.laundryserviceapps

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.laundryserviceapps.ClassModel.Order
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

        add_date_expired()

        btnsavepromotion.setOnClickListener {
            add_promotion()
        }

    }

    fun add_date_expired(){
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        lbl_promo_expired_date.setOnClickListener {
            val date_expired = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                //Display Selected date in TextView
                lbl_promo_expired_date.setText("%d-%d-%d".format(dayOfMonth,monthOfYear+1,year))
            },mYear,mMonth,mDay)
            date_expired.datePicker.minDate = c.getTimeInMillis()
            date_expired.show()
        }
    }

    fun add_promotion(){

//        try {
            val promoname = edtPromotionname.text.toString()
            val discount = edtdiscount.text.toString()

                if (promoname.isEmpty()){
                    edtPromotionname.error = "Promotion name cannot be empty, please enter the promotion name."
                } else if (discount.equals("")){
                    edtdiscount.error = "Please enter the discount value."
                } else{
                    val promo = Promotion()
                    promo.promoID = 0
                    promo.promo_name = promoname
                    promo.discount = discount.toDouble()
                    promo.dateexpired = lbl_promo_expired_date.text.toString()

                    handler.addPromotion(promo)
                    // handler.addPromotion(promotion = Promotion(0,"Laundry Service Off RM 2.0",2.0,"21-7-2020"))
                    Toast.makeText(this,"Promotion has been added successfully.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,PromotionPage::class.java)
                    startActivity(intent)
                }
//        } catch (e: Exception) {
//            Log.i("error", e.toString())
//        }



    }
}