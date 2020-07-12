package com.example.laundryserviceapps

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_service.*

class SelectService : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        imgback.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this)
            mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
            mAlertDialog.setTitle("INFO")
            mAlertDialog.setMessage("Are you sure want to cancel the order? \nOnce you cancelled the order, all the services are ordered will not be saved")
            mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                finish()
                clear()
            }
            mAlertDialog.setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.setNeutralButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
            mAlertDialog.show()

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
            item_selection.add(" Weight Selection - (5 kg) ")
        }else{
            total+=0.0
            item_selection.remove(" Weight Selection - (5 kg) ")
        }
        if(rd9kg.isChecked()){
            total+=8.0
            item_selection.add(" Weight Selection - (9 kg) ")
        }else{
            total+=0.0
            item_selection.remove(" Weight Selection - (9 kg) ")
        }
        if(rd12kg.isChecked()){
            total+=14.0;
            item_selection.add(" Weight Selection - (12 kg) ")
        }else{
            total+=0.0
            item_selection.remove(" Weight Selection - (12 kg) ")
        }

        lbl_payment_amt.text = "Payment: RM " + total

        if(spn_type_laundry_service.selectedItemPosition == 0) {
            Toast.makeText(this,"Please select the type of laundry service.", Toast.LENGTH_LONG).show()

        } else if (radioGroup2.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select the items", Toast.LENGTH_LONG).show()

        } else{
            val intent = Intent(this, PickupAddress::class.java)
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
