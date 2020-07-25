package com.example.laundryserviceapps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var promo_name: String = ""
    private var discount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = intent
        val get_shop = intent.getStringExtra("promo_name")
        val get_address = intent.getStringExtra("discount")

        lblgetpromo_name.text = get_shop
        lblgetpromo_discount.text = get_address

        img_history.setOnClickListener {
            val intent = Intent(this,History::class.java)
            startActivity(intent)
        }

        img_order_tracking.setOnClickListener {
            val intent = Intent(this,MyOrderList::class.java)
            startActivity(intent)
        }

        img_place_order.setOnClickListener {

            if(lblgetpromo_name.text == "" && lblgetpromo_discount.text == ""){
                sharedPreferencesEmpty()
            }else{
                sharedPreferences()
            }
            val intent = Intent(this,LaundryShopSelectionList::class.java)
            if(lblgetpromo_name.text == "" && lblgetpromo_discount.text == ""){
                intent.putExtra("promo_name1","No Promotion")
                intent.putExtra("discount1","0.0")
            }else{
                intent.putExtra("promo_name1",lblgetpromo_name.text.toString())
                intent.putExtra("discount1",lblgetpromo_discount.text.toString())
            }
            startActivity(intent)
        }
        img_product_item.setOnClickListener(){
            val intent = Intent(this,product_laundryMainMenu::class.java)
            startActivity(intent)
        }

        img_promotion.setOnClickListener {
            val intent = Intent(this,PromotionPage::class.java)
            startActivity(intent)
        }

        btnlogout.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this)
            mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
            mAlertDialog.setTitle("INFO")
            mAlertDialog.setMessage("Are you sure want to logout?")
            mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                onBackPressed()
                finish()
            }
            mAlertDialog.setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.setNeutralButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
            mAlertDialog.show()
        }

    }

    fun sharedPreferences(){
        sharedPreferences = getSharedPreferences("Promotion", Context.MODE_PRIVATE)
        promo_name = sharedPreferences.getString("Promo_Name",lblgetpromo_name.text.toString())?: return
        discount = sharedPreferences.getString("Promo_Discount",lblgetpromo_discount.text.toString())?: return

        promo_name = lblgetpromo_name.text.toString()
        discount = lblgetpromo_discount.text.toString()

        with(sharedPreferences.edit()){
            putString("Promo_Name",promo_name)
            putString("Promo_Discount",discount)
            apply()
        }
    }

    fun sharedPreferencesEmpty(){
        sharedPreferences = getSharedPreferences("Promotion", Context.MODE_PRIVATE)
        promo_name = sharedPreferences.getString("Promo_Name",lblgetpromo_name.text.toString())?: return
        discount = sharedPreferences.getString("Promo_Discount",lblgetpromo_discount.text.toString())?: return

        promo_name = lblgetpromo_name.text.toString()
        discount = lblgetpromo_discount.text.toString()

        with(sharedPreferences.edit()){
            putString("Promo_Name","No Promotion")
            putString("Promo_Discount","0.0")
            apply()
        }
    }

}
