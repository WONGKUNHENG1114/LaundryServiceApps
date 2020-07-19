package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test.*

class MainActivity : AppCompatActivity() {

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
            val intent = Intent(this,LaundryShopList::class.java)
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

}
