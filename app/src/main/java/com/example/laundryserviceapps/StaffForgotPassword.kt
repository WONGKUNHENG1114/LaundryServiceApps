package com.example.laundryserviceapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_staff_forgot_password.*

class StaffForgotPassword : AppCompatActivity() {
    lateinit var handler: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_forgot_password)

        handler = SQLiteHelper(this)

        btnStaffchangePwd.setOnClickListener {
            change_password_validation()
        }

        imgback10.setOnClickListener {
            clear()
            finish()
        }

    }

    fun change_password_validation(){
        val Staffemail = edtStaffEmailAddress.text.toString()
        val Staffnewpassword = edtStaffNewPwd.text.toString()
        val Staffconfirmpassword = edtStaffConfirmPwd.text.toString()

        val str: String = Staffnewpassword
        val pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$".toRegex()

        if(Staffemail.isEmpty()) {
            edtEmailAddress.error = "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if (Staffnewpassword.isEmpty()) {
            edtNewPwd.error =
                "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if(Staffconfirmpassword.isEmpty()){
            edtConfirmPwd.error = "The compulsory fields cannot be empty, please enter the compulsory field."

        }else if (!Patterns.EMAIL_ADDRESS.matcher(Staffemail).matches()){
            edtEmailAddress.error = "Please enter the valid email."
        }else if (!pattern.matches(str)){
            edtNewPwd.error = "Weak password, minimum 8 or more digits, including combine with special character, numbers and capital and small letter."
        }else if (!Staffnewpassword.equals(Staffconfirmpassword)){
            edtConfirmPwd.error = "Confirm password is not matched with new password."
        }else{
            handler.changeStaffPassword(Staffemail,Staffconfirmpassword)
            Toast.makeText(this,"Password has been reset.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun clear(){
        edtStaffEmailAddress.setText("")
        edtStaffNewPwd.setText("")
        edtStaffConfirmPwd.setText("")
    }
}