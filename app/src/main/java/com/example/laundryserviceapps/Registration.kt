package com.example.laundryserviceapps

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_registration.*
import java.text.SimpleDateFormat
import java.util.*

class Registration : AppCompatActivity() {

    lateinit var handler: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        handler = SQLiteHelper(this)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        lbl_date_registration.text = formatedDate.toString()

        btnregister.setOnClickListener{
            register_user()
        }

        imgback.setOnClickListener {
            onBackPressed()
            clear()
        }
    }

    fun register_user(){
        val firstname = edtFirstname.text.toString()
        val lastname = edtLastName.text.toString()
        val email = edtEmail.text.toString()
        val phone = edtPhone.text.toString()
        val user_mode = lblaccountMode.text.toString()
        val newpassword = edtNewpassword.text.toString()
        val confirmpassword = edtConfirmpassword.text.toString()

        val str: String = newpassword
        val pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$".toRegex()

        if(firstname.isEmpty()) {
            edtFirstname.error = "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if (lastname.isEmpty()) {
            edtLastName.error = "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if (email.isEmpty()) {
            edtEmail.error = "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if (phone.equals("")) {
            edtPhone.error = "The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if (newpassword.isEmpty()) {
            edtNewpassword.error ="The compulsory fields cannot be empty, please enter the compulsory field."
        }
        if (confirmpassword.isEmpty()){
            edtConfirmpassword.error = "The compulsory fields cannot be empty, please enter the compulsory field."
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.error = "Please enter the valid email."
            Toast.makeText(this,"Please enter the valid email.", Toast.LENGTH_LONG).show()
        }else if (phone.length <= 9 || phone.length > 10){
            edtPhone.error = "Please enter the valid phone number."
        }else if (!pattern.matches(str)){
            edtNewpassword.error = "Weak password, minimum 7 or more digits, including combine with special character, numbers and capital and small letter."
            Toast.makeText(this,"Weak password, minimum 7 or more digits, including combine with special character, numbers and capital and small letter.", Toast.LENGTH_LONG).show()
        }else if (!newpassword.equals(confirmpassword)){
            edtConfirmpassword.error = "Confirm password is not matched with new password."
        }else{
            handler.insertCustomerData(firstname, lastname, email, phone.toLong(), user_mode, confirmpassword, lbl_date_registration.text.toString())
            Toast.makeText(this,"User account has been registered.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun clear(){
        edtFirstname.setText("")
        edtLastName.setText("")
        edtEmail.setText("")
        edtPhone.setText("")
        edtNewpassword.setText("")
        edtConfirmpassword.setText("")
    }
}
