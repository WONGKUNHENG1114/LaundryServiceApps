package com.example.laundryserviceapps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_delivery_address.*

class DeliveryAddress : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_address)

        imgback2.setOnClickListener {
            onBackPressed()
        }

        btnNext3.setOnClickListener {
            proceed_to_next()
        }

        val i = intent
        val get_add = i.getStringExtra("PICKUP_ADDRESS")
        val get_city = i.getStringExtra("PICKUP_CITY")
        val get_poscode = i.getStringExtra("PICKUP_POSCODE")
        val get_state = i.getStringExtra("PICKUP_STATE")

        edt_delivery_address.setText(get_add)
        edt_delivery_city.setText(get_city)
        edt_delivery_poscode.setText(get_poscode)
        edt_delivery_state.setText(get_state)

        val get_laundry_service_p3 = i.getStringExtra("P2LAUNDRY_TYPE")
        val get_payment_amt_p3 = i.getStringExtra("P2PAYMENTAMT")
        val get_address_pickup_p2 = i.getStringExtra("P2PICKUP_ADDRESS")
        val get_city_pickup_p2 = i.getStringExtra("P2PICKUP_CITY")
        val get_poscode_p2 = i.getStringExtra("P2PICKUP_POSCODE")
        val get_state_p2 = i.getStringExtra("P2PICKUP_STATE")
        val get_list_item_selection_p2 = i.getStringExtra("P2LISTITEM")
        val get_full_address = "$get_address_pickup_p2, $get_city_pickup_p2, $get_poscode_p2, $get_state_p2"


        lbl_get_laundry_service_p3.setText(get_laundry_service_p3)
        lbl_get_payment_amt_p3.setText(get_payment_amt_p3)
        lbl_get_address_pickup_p2.setText(get_full_address)
        lbl_get_list_item2.setText(get_list_item_selection_p2)

    }

    fun proceed_to_next(){

        val delivery_add = edt_delivery_address.toString()
        val delivery_city = edt_delivery_city.toString()
        val delivery_poscode = edt_delivery_poscode.toString()
        val delivery_state = edt_delivery_state.toString()

        val laundry_service_p3 = lbl_get_laundry_service_p3.text.toString()
        val payment_amt_p3 = lbl_get_payment_amt_p3.text.toString()
        val address_pickup_p2 = lbl_get_address_pickup_p2.text.toString()
        val list_item_selection_p2 = lbl_get_list_item2.text.toString()

        val address_delivery_p3 = edt_delivery_address.text.toString()
        val city_delivery_p3 = edt_delivery_city.text.toString()
        val poscode_delivery_p3 =  edt_delivery_poscode.text.toString()
        val state_delivery_p3 = edt_delivery_state.text.toString()

        if(delivery_add.isEmpty()){
            edt_delivery_address.error = "Address cannot be empty, please enter the address."
        }
        if(delivery_city.isEmpty()){
            edt_delivery_city.error = "City cannot be empty, please enter the city."
        }
        if(delivery_poscode.isEmpty()){
            edt_delivery_poscode.error = "Poscode cannot be empty, please enter the poscode."
        }
        if(delivery_state.isEmpty()){
            edt_delivery_state.error = "State cannot be empty, please enter the state."
        }else{
            val intent = Intent(this,SelectTimeSlot::class.java)
            intent.putExtra("P3LAUNDRY_TYPE",laundry_service_p3)
            intent.putExtra("P3PAYMENTAMT",payment_amt_p3)
            intent.putExtra("P3PICKUP_ADDRESS",address_pickup_p2)

            intent.putExtra("P3DELIVERY_ADDRESS",address_delivery_p3)
            intent.putExtra("P3DELIVERY_CITY",city_delivery_p3)
            intent.putExtra("P3DELIVERY_POSCODE",poscode_delivery_p3)
            intent.putExtra("P3DELIVERY_STATE",state_delivery_p3)
            intent.putExtra("P3LISTITEM",list_item_selection_p2)
            startActivity(intent)
        }
    }
}
