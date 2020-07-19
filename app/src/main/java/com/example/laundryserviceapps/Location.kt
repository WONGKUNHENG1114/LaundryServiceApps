package com.example.laundryserviceapps

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.activity_pickup_address.*

class Location : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val i = intent
        val get_laundry_service_type = i.getStringExtra("LAUNDRY_TYPE")
        val get_payment_amount = i.getStringExtra("PAYMENTAMT")
        val get_laundry_shop = i.getStringExtra("Shop2")
        val get_laundry_shop_address = i.getStringExtra("Address2")

        val b = intent.extras
        if (null != b) {
            val get_item_selection = b.getStringArrayList("ITEMSELECTION1")
            lbl_get_list_item2.text = get_item_selection?.toMutableList().toString()
        }

        lbl_getshopname2.setText(get_laundry_shop)
        lbl_getshopaddress2.setText(get_laundry_shop_address)

        lbl_get_laundry_service2.setText(get_laundry_service_type)
        lbl_get_payment_amt22.setText(get_payment_amount)

        btnlocationpickup.setOnClickListener {
            val uriIntent1 = Uri.parse("geo:0,0?q=${edt_get_pickup_address.text.toString()}")
            val MapIntent1 = Intent(Intent.ACTION_VIEW,uriIntent1)
            MapIntent1.setPackage("com.google.android.apps.maps")
            startActivity(MapIntent1)
        }

        btnlocationdelivery.setOnClickListener {
            val uriIntent2 = Uri.parse("geo:0,0?q=${edt_delivery_address.text.toString()}")
            val MapIntent2 = Intent(Intent.ACTION_VIEW,uriIntent2)
            MapIntent2.setPackage("com.google.android.apps.maps")
            startActivity(MapIntent2)
            startActivity(MapIntent2)
        }

        btnNext4.setOnClickListener {
            validation_field()
        }
    }

    fun validation_field(){
        val pickupaddress = edt_get_pickup_address.text.toString()
        val deliveryaddress = edt_delivery_address.text.toString()

        if(pickupaddress.isEmpty()){
            edt_get_pickup_address.error = "Pickup address cannot be empty, please enter the pickup address."
        }else if(deliveryaddress.isEmpty()){
            edt_delivery_address.error = "Delivery address cannot be empty, please enter the delivery address."
        }else {
            val intent = Intent(this, SelectTimeSlot::class.java)

            intent.putExtra("P3LAUNDRY_TYPE", lbl_get_laundry_service2.text.toString())
            intent.putExtra("P3PAYMENTAMT", lbl_get_payment_amt22.text.toString())
            intent.putExtra("Shop3", lbl_getshopname2.text.toString())
            intent.putExtra("Address3", lbl_getshopaddress2.text.toString())
            intent.putExtra("P3LISTITEM", lbl_get_list_item2.text.toString())

            intent.putExtra("P3PICKUP_ADDRESS", pickupaddress)
            intent.putExtra("DELIVERYADDRESS", deliveryaddress)
            startActivity(intent)
        }
    }
}