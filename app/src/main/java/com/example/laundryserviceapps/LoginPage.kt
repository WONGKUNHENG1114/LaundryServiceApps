package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_login_page.edtEmail

class LoginPage : AppCompatActivity() {

    lateinit var handler: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        handler = SQLiteHelper(this)

        btnlogin.setOnClickListener {
            login_main_portal()
        }

        txtUserRegister.setOnClickListener{
            val intent = Intent(this,Registration::class.java)
            startActivity(intent)
            clear()
        }

        txtForgotPassword.setOnClickListener {
            val intent = Intent(this,ForgotPassword::class.java)
            startActivity(intent)
            clear()
        }
    }

    fun login_main_portal(){
        val login_email = edtEmail.text.toString()
        val login_password = edtPwd.text.toString()

        if(login_email.isEmpty()){
            edtEmail.error = "Email address cannot be empty, please enter the email address."
        }

        if(login_password.isEmpty()){
            edtPwd.error = "Password cannot be empty, please enter the password."

        } else if(handler.checkUser(login_email,login_password)){
            val intent = Intent(this,MainActivity::class.java)
            Toast.makeText(this,"Login successfully.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            clear()

        } else {
            Toast.makeText(this,"Login failed due to wrong email or password, please try again to login.", Toast.LENGTH_SHORT).show()
        }

    }

    fun clear(){
        edtEmail.setText("")
        edtPwd.setText("")
    }
}
