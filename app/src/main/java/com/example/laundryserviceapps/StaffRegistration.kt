package com.example.laundryserviceapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_staff_registration.*
import java.text.SimpleDateFormat
import java.util.*

class StaffRegistration : AppCompatActivity() {

    lateinit var handler: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_registration)

        handler = SQLiteHelper(this)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        lblStaff_date_registration.text = formatedDate.toString()

        btnstaffregister.setOnClickListener{
            register_staff()
        }

        imgback9.setOnClickListener {
            onBackPressed()
            clear()
        }
    }

    fun register_staff(){
        try{
            handler.onCreateStaff()
            val s_firstname = edtStaffFirstname2.text.toString()
            val s_lastname = edtStaffLastName.text.toString()
            val s_email = edtStaffEmail.text.toString()
            val s_phone = edtStaffPhone.text.toString()
            val s_user_mode = lblaccountMode2.text.toString()
            val s_newpassword = edtStaffNewpassword.text.toString()
            val s_confirmpassword = edtStaffConfirmpassword.text.toString()

            val str: String = s_newpassword
            val pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$".toRegex()

            if(s_firstname.isEmpty()) {
                edtFirstname.error = "The compulsory fields cannot be empty, please enter the compulsory field."
            }
            if (s_lastname.isEmpty()) {
                edtLastName.error = "The compulsory fields cannot be empty, please enter the compulsory field."
            }
            if (s_email.isEmpty()) {
                edtEmail.error = "The compulsory fields cannot be empty, please enter the compulsory field."
            }
            if (s_phone.equals("")) {
                edtPhone.error = "The compulsory fields cannot be empty, please enter the compulsory field."
            }
            if (s_newpassword.isEmpty()) {
                edtNewpassword.error ="The compulsory fields cannot be empty, please enter the compulsory field."
            }
            if (s_confirmpassword.isEmpty()){
                edtConfirmpassword.error = "The compulsory fields cannot be empty, please enter the compulsory field."
            } else if (!Patterns.EMAIL_ADDRESS.matcher(s_email).matches()){
                edtEmail.error = "Please enter the valid email."
                Toast.makeText(this,"Please enter the valid email.", Toast.LENGTH_LONG).show()
            }else if (s_phone.length <= 9 || s_phone.length > 10){
                edtPhone.error = "Please enter the valid phone number."
            }else if (!pattern.matches(str)){
                edtNewpassword.error = "Weak password, minimum 8 or more digits, including combine with special character, numbers and capital and small letter."
                Toast.makeText(this,"Weak password, minimum 8 or more digits, including combine with special character, numbers and capital and small letter.", Toast.LENGTH_LONG).show()
            }else if (!s_newpassword.equals(s_confirmpassword)){
                edtConfirmpassword.error = "Confirm password is not matched with new password."
            }else{
                handler.insertStaffData(s_firstname, s_lastname, s_email, s_phone.toLong(), s_user_mode, s_confirmpassword, lblStaff_date_registration.text.toString())
                Toast.makeText(this,"Admin account has been registered.", Toast.LENGTH_LONG).show()
                finish()
            }
        } catch (e: Exception) {
            Log.i("error", e.toString())
        }

    }

    fun clear(){
        edtStaffFirstname2.setText("")
        edtStaffLastName.setText("")
        edtStaffEmail.setText("")
        edtStaffPhone.setText("")
        edtStaffNewpassword.setText("")
        edtStaffConfirmpassword.setText("")
    }
}