package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img_product_item.setOnClickListener {

        }

        img_history.setOnClickListener {
            val intent = Intent(this,History::class.java)
            startActivity(intent)
        }

        img_order_tracking.setOnClickListener {
            val intent = Intent(this,MyOrderList::class.java)
            startActivity(intent)
        }

        img_place_order.setOnClickListener {
            val intent = Intent(this,SelectService::class.java)
            startActivity(intent)
        }
        img_product_item.setOnClickListener(){
            val intent = Intent(this,product_laundryMainMenu::class.java)
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
