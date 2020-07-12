package com.example.laundryserviceapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {

    lateinit var handler: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        handler = SQLiteHelper(this)

        btnchangePwd.setOnClickListener {
            change_password_validation()
        }

        imgback4.setOnClickListener {
            clear()
            finish()
        }
    }

    fun change_password_validation(){
        val email = edtEmailAddress.text.toString()
        val newpassword = edtNewPwd.text.toString()
        val confirmpassword = edtConfirmPwd.text.toString()

        val str: String = newpassword
        val pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$".toRegex()

        if(email.isEmpty()) {
            edtEmailAddress.error = "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if (newpassword.isEmpty()) {
            edtNewPwd.error =
                "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if(confirmpassword.isEmpty()){
            edtConfirmPwd.error = "The compulsory fields cannot be empty, please enter the compulsory field."

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailAddress.error = "Please enter the valid email."
        }else if (!pattern.matches(str)){
            edtNewPwd.error = "Weak password, minimum 7 or more digits, including combine with special character, numbers and capital and small letter."
        }else if (!newpassword.equals(confirmpassword)){
            edtConfirmPwd.error = "Confirm password is not matched with new password."
        }else{
            handler.changePassword(email,confirmpassword)
            Toast.makeText(this,"Password has been reset.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun clear(){
        edtEmailAddress.setText("")
        edtNewPwd.setText("")
        edtConfirmPwd.setText("")
    }
}
