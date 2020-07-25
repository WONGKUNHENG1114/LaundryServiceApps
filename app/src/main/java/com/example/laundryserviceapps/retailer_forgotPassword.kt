package com.example.laundryserviceapps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_staff_forgot_password.*
import kotlinx.android.synthetic.main.retailer_user_forgot_password.*
import kotlinx.android.synthetic.main.retailer_user_forgot_password.btnStaffchangePwd
import kotlinx.android.synthetic.main.retailer_user_forgot_password.edtStaffConfirmPwd
import kotlinx.android.synthetic.main.retailer_user_forgot_password.edtStaffEmailAddress
import kotlinx.android.synthetic.main.retailer_user_forgot_password.edtStaffNewPwd


class retailer_forgotPassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_user_forgot_password)

//        handler = SQLiteHelper(this)
//
//        btnStaffchangePwd.setOnClickListener {
//            change_password_validation()
//        }
//
//    }
//    fun change_password_validation() {
//        val Staffemail = edtStaffEmailAddress.text.toString()
//        val Staffnewpassword = edtStaffNewPwd.text.toString()
//        val Staffconfirmpassword = edtStaffConfirmPwd.text.toString()
//
    }
}