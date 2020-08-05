package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laundryserviceapps.DatabaseHandler.retailer_databaseHandler
import kotlinx.android.synthetic.main.retailer_user_login.*

class retailer_login : AppCompatActivity() {
    val db = retailer_databaseHandler(this)


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_user_login)
        val db = retailer_databaseHandler(this)
        val Sqlite=db.writableDatabase
        db.close()
        val pref:SharedPreferences = this.getSharedPreferences("retailer_user_details", MODE_PRIVATE)
        scrollViewId.setOnTouchListener { v, _ ->
            hideSoftKeyboard(v)
            false
        }

        btnSlogin.setOnClickListener {
            checkUserExists(pref)
        }

        txtRetailerRegister.setOnClickListener {
            val intent = Intent(this, retailer_registration::class.java)
            startActivity(intent)

        }
        txtForgotPassword2.setOnClickListener {
            val intent = Intent(this, retailer_forgotPassword::class.java)
            startActivity(intent)

        }
        btnMainMenu.setOnClickListener {
            val intent = Intent(this, WelcomeLaundryApp::class.java)
            startActivity(intent)
        }


    }
    private fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    private fun checkUserExists(pref:SharedPreferences)
    {
         val userName = edtStaffUsername.text.toString()
         val userPassword=edtStaffPwd.text.toString()
        if (db.MatchUserProfile(userName,userPassword))
        {
            Toast.makeText(this,"Longing in...", Toast.LENGTH_SHORT).show()

            val edit: SharedPreferences.Editor = pref.edit()
            edit.putString("username", userName)
            edit.apply()
            val intent = Intent(this, product_laundryMainMenu::class.java)
            startActivity(intent)
        }
        else
            Toast.makeText(this,"The user account does not exists", Toast.LENGTH_LONG).show()
    }

}