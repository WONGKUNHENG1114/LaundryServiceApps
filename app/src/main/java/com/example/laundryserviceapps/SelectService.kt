package com.example.laundryserviceapps

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_test.*

class SelectService : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        var intent = intent
        val get_shop = intent.getStringExtra("Shop")
        val get_address = intent.getStringExtra("Address")

        lbl_getshopname.text = get_shop
        lbl_getshopaddress.text = get_address
        lblgetpromo_name3.text =
            lblgetpromo_discount3.text =

        imgback.setOnClickListener {
            finish()
            clear()
        }

        btnNext.setOnClickListener {
            proceed_to_next()
        }

        btnreset.setOnClickListener {
            clear()
        }

    }

    fun proceed_to_next(){
        val item_selection: ArrayList<String> = ArrayList()
        var total = 0.0

        //jeans
        if(rd5kg.isChecked()){
            total+=6.0;
            item_selection.add(" (Common Clothes) 1 - 12  pieces clothes (RM 6.00) ")
        }else{
            total+=0.0
            item_selection.remove(" (Common Clothes) 1 - 12  pieces clothes (RM 6.00) ")
        }
        if(rd9kg.isChecked()){
            total+=8.0
            item_selection.add(" (Common Clothes) 12 - 24  pieces clothes (RM 8.00) ")
        }else{
            total+=0.0
            item_selection.remove(" (Common Clothes) 12 - 24  pieces clothes (RM 8.00) ")
        }
        if(rd12kg.isChecked()){
            total+=14.0;
            item_selection.add(" (Common Clothes) More than 24 pieces clothes - (RM 14.00) ")
        }else{
            total+=0.0
            item_selection.remove(" (Common Clothes) More than 24 pieces clothes - (RM 14.00) ")
        }

        lbl_payment_amt.text = "Payment: RM " + total

        if(spn_type_laundry_service.selectedItemPosition == 0) {
            Toast.makeText(this,"Please select the type of laundry service.", Toast.LENGTH_LONG).show()

        } else if (radioGroup2.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select the items", Toast.LENGTH_LONG).show()

        } else{
            val intent = Intent(this, Location::class.java)
            intent.putExtra("Shop2",lbl_getshopname.text.toString())
            intent.putExtra("Address2",lbl_getshopaddress.text.toString())

            intent.putExtra("LAUNDRY_TYPE",spn_type_laundry_service.selectedItem.toString())
            intent.putExtra("PAYMENTAMT",total.toString())
            intent.putStringArrayListExtra("ITEMSELECTION1", item_selection)
            startActivity(intent)
        }
    }


    fun clear(){
        spn_type_laundry_service.setSelection(0)
        radioGroup2.clearCheck()
        lbl_payment_amt.text = "Payment: RM 0.0"
    }

}
