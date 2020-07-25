package com.example.laundryserviceapps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_laundry_shop_list.*
import kotlinx.android.synthetic.main.activity_select_service.*

class SelectService : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        var intent = intent
        val get_shop = intent.getStringExtra("Shop")
        val get_address = intent.getStringExtra("Address")

        lbl_getshopname.setText(get_shop)
        lbl_getshopaddress.setText(get_address)

        val sharedPreferences = getSharedPreferences("Promotion", Context.MODE_PRIVATE)
        val promo_name = sharedPreferences.getString("Promo_Name",lblget_promo.text.toString())
        val discount = sharedPreferences.getString("Promo_Discount",lbl_get_discount.text.toString())

        lblget_promo.text = String.format("%s%s","",promo_name)
        lbl_get_discount.text = String.format("%s%s", "",discount)

//        lblget_promo.setText(get_promo_name)
//        lbl_get_discount.setText(get_promo_discount)

//        if(lblgetpromo_name.text == "" && lblgetpromo_discount.text == ""){
//            intent.putExtra("promo_name1","No Promotion")
//            intent.putExtra("discount1","0.0")
//        }else{
//            intent.putExtra("promo_name1",lblgetpromo_name.text.toString())
//            intent.putExtra("discount1",lblgetpromo_discount.text.toString())
//        }

//        lblAskForPromotion.setOnClickListener {
//            val intent = Intent(this,PromotionPage::class.java)
//            startActivity(intent)
//        }

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

        var discountprice = lbl_get_discount.text.toString()

        if(spn_type_laundry_service.selectedItemPosition == 1) {
            if (rd5kg.isChecked()) {
                total += 4.5;
                item_selection.add(" (Common Clothes) 1 - 12  pieces clothes & Wash and Fold (RM 4.50) ")
            } else {
                total += 0.0
                item_selection.remove(" (Common Clothes) 1 - 12  pieces clothes & Wash and Fold (RM 4.50) ")
            }
            if (rd9kg.isChecked()) {
                total += 6.0
                item_selection.add(" (Common Clothes) 12 - 24  pieces clothes & Wash and Fold (RM 6.00) ")
            } else {
                total += 0.0
                item_selection.remove(" (Common Clothes) 12 - 24  pieces clothes & Wash and Fold (RM 6.00) ")
            }
            if (rd12kg.isChecked()) {
                total += 10.0;
                item_selection.add(" (Common Clothes) More than 24 pieces clothes & Wash and Fold (RM 10.00) ")
            } else {
                total += 0.0
                item_selection.remove(" (Common Clothes) More than 24 pieces clothes & Wash and Fold (RM 10.00) ")
            }
        }

        if(spn_type_laundry_service.selectedItemPosition == 2){
            if(rd5kg.isChecked()){
                total+=5.5;
                item_selection.add(" (Common Clothes) 1 - 12  pieces clothes & Wash and Iron (RM 5.50) ")
            }else{
                total+=0.0
                item_selection.remove(" (Common Clothes) 1 - 12  pieces clothes & Wash and Iron (RM 5.50) ")
            }
            if(rd9kg.isChecked()){
                total+=7.0
                item_selection.add(" (Common Clothes) 12 - 24  pieces clothes & Wash and Iron (RM 7.00) ")
            }else{
                total+=0.0
                item_selection.remove(" (Common Clothes) 12 - 24  pieces clothes & Wash and Iron (RM 7.00) ")
            }
            if(rd12kg.isChecked()){
                total+=12.0;
                item_selection.add(" (Common Clothes) More than 24 pieces clothes & Wash and Iron (RM 12.00) ")
            }else{
                total+=0.0
                item_selection.remove(" (Common Clothes) More than 24 pieces clothes & Wash and Iron (RM 12.00) ")
            }
        }

        if(spn_type_laundry_service.selectedItemPosition == 3){
            if(rd5kg.isChecked()){
                total+=6.0;
                item_selection.add(" (Common Clothes) 1 - 12  pieces clothes & Ironing (RM 6.00) ")
            }else{
                total+=0.0
                item_selection.remove(" (Common Clothes) 1 - 12  pieces clothes & Ironing (RM 6.00) ")
            }
            if(rd9kg.isChecked()){
                total+=8.0
                item_selection.add(" (Common Clothes) 12 - 24  pieces clothes & Ironing (RM 8.00) ")
            }else{
                total+=0.0
                item_selection.remove(" (Common Clothes) 12 - 24  pieces clothes & Ironing (RM 8.00) ")
            }
            if(rd12kg.isChecked()){
                total+=14.0;
                item_selection.add(" (Common Clothes) More than 24 pieces clothes & Ironing (RM 14.00) ")
            }else{
                total+=0.0
                item_selection.remove(" (Common Clothes) More than 24 pieces clothes & Ironing (RM 14.00) ")
            }
        }

        lbl_payment_amt.text = "Payment: RM " + (total - discountprice.toDouble())

        if(spn_type_laundry_service.selectedItemPosition == 0) {
            Toast.makeText(this,"Please select the type of laundry service.", Toast.LENGTH_LONG).show()

        } else if (radioGroup2.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select the items", Toast.LENGTH_LONG).show()

        } else{
            val intent = Intent(this, Location::class.java)
            intent.putExtra("Shop2",lbl_getshopname.text.toString())
            intent.putExtra("Address2",lbl_getshopaddress.text.toString())

            if(lblget_promo.equals("") && lbl_get_discount.equals("")){
                intent.putExtra("PROMOTION_NAME","No Promotion")
                intent.putExtra("PROMOTION_PRICE","0.0")
            }else{
                intent.putExtra("PROMOTION_NAME",lblget_promo.text.toString())
                intent.putExtra("PROMOTION_PRICE",lbl_get_discount.text.toString())
            }

            intent.putExtra("LAUNDRY_TYPE",spn_type_laundry_service.selectedItem.toString())
            intent.putExtra("PAYMENTAMT",(total - discountprice.toDouble()).toString())

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
