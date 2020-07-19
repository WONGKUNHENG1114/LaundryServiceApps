package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_staff_login_page.*

class StaffLoginPage : AppCompatActivity() {
    lateinit var handler: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_login_page)
        handler = SQLiteHelper(this)

        btnSlogin.setOnClickListener {
            login_main_portal()
        }

        txtStaffRegister.setOnClickListener{
            val intent = Intent(this,StaffRegistration::class.java)
            startActivity(intent)
            clear()
        }

        txtForgotPassword2.setOnClickListener {
            val intent = Intent(this,StaffForgotPassword::class.java)
            startActivity(intent)
            clear()
        }
    }

    fun login_main_portal(){
        val s_login_email = edtStaffEmail.text.toString()
        val s_login_password = edtStaffPwd.text.toString()

        if(s_login_email.isEmpty()){
            edtStaffEmail.error = "Email address cannot be empty, please enter the email address."
        }

        if(s_login_password.isEmpty()){
            edtStaffPwd.error = "Password cannot be empty, please enter the password."

        } else if(handler.checkStaffUser(s_login_email,s_login_password)){
            val intent = Intent(this,product_laundryMainMenu::class.java)
            Toast.makeText(this,"Login successfully.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            clear()

        } else {
            Toast.makeText(this,"Login failed due to wrong email or password, please try again to login.", Toast.LENGTH_SHORT).show()
        }

    }

    fun clear(){
        edtStaffEmail.setText("")
        edtStaffPwd.setText("")
    }
}