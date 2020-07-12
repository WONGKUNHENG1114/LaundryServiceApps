package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_delivery_address.*
import kotlinx.android.synthetic.main.activity_pickup_address.*
import kotlinx.android.synthetic.main.activity_pickup_address.imgback
import kotlinx.android.synthetic.main.activity_select_time_slot.*

class PickupAddress : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup_address)

        val i = intent
        val get_laundry_service_type = i.getStringExtra("LAUNDRY_TYPE")
        val get_payment_amount = i.getStringExtra("PAYMENTAMT")

        lbl_get_laundry_service.setText(get_laundry_service_type)
        lbl_get_payment_amt2.setText(get_payment_amount)

        val b = intent.extras
        if (null != b) {
            val get_item_selection = b.getStringArrayList("ITEMSELECTION1")
            lbl_get_list_item.text = get_item_selection?.toMutableList().toString()
        }



        btnNext2.setOnClickListener {
            proceed_to_next()
        }

        imgback.setOnClickListener {
            onBackPressed()
        }
    }

    fun proceed_to_next(){
        val pickup_add = edt_address.text.toString()
        val pickup_city = edt_city.text.toString()
        val pickup_poscode = edt_poscode.text.toString()
        val pickup_state = edt_state.text.toString()
        val laundry_service = lbl_get_laundry_service.text.toString()
        val get_payment_amt = lbl_get_payment_amt2.text.toString()
        val list_item_selection2 = lbl_get_list_item.text.toString()

        if(pickup_add.isEmpty()) {
            edt_address.error = "Address cannot be empty, please enter the address."
        }
        if(pickup_city.isEmpty()) {
            edt_city.error = "City cannot be empty, please enter the city."
        }
        if(pickup_poscode.isEmpty()){
            edt_poscode.error = "Poscode cannot be empty, please enter the poscode."
        }
        if(pickup_state.isEmpty()){
            edt_state.error = "State cannot be empty, please enter the state."
        }else {
            if(chk_same_add.isChecked){
                val intent = Intent(this,DeliveryAddress::class.java)
                intent.putExtra("P2LAUNDRY_TYPE",laundry_service)
                intent.putExtra("P2PAYMENTAMT",get_payment_amt)
                intent.putExtra("PICKUP_ADDRESS",pickup_add)
                intent.putExtra("PICKUP_CITY",pickup_city)
                intent.putExtra("PICKUP_POSCODE",pickup_poscode)
                intent.putExtra("PICKUP_STATE",pickup_state)
                intent.putExtra("P2PICKUP_ADDRESS",pickup_add)
                intent.putExtra("P2PICKUP_CITY",pickup_city)
                intent.putExtra("P2PICKUP_POSCODE",pickup_poscode)
                intent.putExtra("P2PICKUP_STATE",pickup_state)
                intent.putExtra("P2LISTITEM",list_item_selection2)
                startActivity(intent)
            }else{
                val intent = Intent(this,DeliveryAddress::class.java)
                intent.putExtra("P2LAUNDRY_TYPE",laundry_service)
                intent.putExtra("P2PAYMENTAMT",get_payment_amt)
                intent.putExtra("P2PICKUP_ADDRESS",pickup_add)
                intent.putExtra("P2PICKUP_CITY",pickup_city)
                intent.putExtra("P2PICKUP_POSCODE",pickup_poscode)
                intent.putExtra("P2PICKUP_STATE",pickup_state)
                intent.putExtra("P2LISTITEM",list_item_selection2)
                startActivity(intent)
            }
        }

    }
}
