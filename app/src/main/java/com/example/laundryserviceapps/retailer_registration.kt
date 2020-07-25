package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.laundryserviceapps.ClassModel.RetailerUser
import com.example.laundryserviceapps.DatabaseHandler.retailer_databaseHandler
import kotlinx.android.synthetic.main.retailer_user_registration.*
import kotlinx.android.synthetic.main.retailer_user_registration.scrollViewId
import kotlinx.android.synthetic.main.test3.*

import java.util.regex.Pattern


class retailer_registration : AppCompatActivity() {

//    private var usernameValid: Boolean = false
//    private var emailValid: Boolean = false
//    private var phoneValid: Boolean = false
//    private var userPasswordValid: Boolean = false
//    private var confirmPasswordValid: Boolean = false
    private val db = retailer_databaseHandler(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_user_registration)
        db.onCreateRetailerUser()
        btnRegister.setOnClickListener {
            register_user()
        }

        scrollViewId.setOnTouchListener { v, _ ->
            hideSoftKeyboard(v)
            false
        }

        edtuserName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
               userNameValidation()
            }
        }

        edtEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                emailValidation()

            }
        }

        edtPhoneNo.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
              phoneValidation()
            }
        }
        edtPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
             userPasswordValidation()
            }
        }
        edtConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
               confirmPasswordValidation()
            }
        }

    }

    private fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun register_user() {
        val usernameValid = userNameValidation()
        val emailValid = emailValidation()
        val phoneValid = phoneValidation()
        val  userPasswordValid = userPasswordValidation()
        val  confirmPasswordValid = confirmPasswordValidation()
        if (!usernameValid ||
            !emailValid ||
            !phoneValid ||
            !userPasswordValid ||
            !confirmPasswordValid
        ) {
            Toast.makeText(this, "Please check field above.", Toast.LENGTH_LONG).show()
        } else
            dialogBuilder()

    }

    fun userNameValidation(): Boolean {
        val userName = edtuserName.text.toString()
        val sPattern =
            Pattern.compile("(^[a-z])([a-z0-9]{4,})")//match the pattern insert in the shop name
        val match = sPattern.matcher(userName)
        return if (userName.isEmpty()) {
                edtuserName.error = "username cannot empty"
                false
            }
        else if (!match.find())
        {
            edtuserName.error =
                "Invalid format.\n${resources.getString(R.string.bullet)}Small alphabet and number only and at least 5 character" +
                        "\n${resources.getString(R.string.bullet)}Recommend username:ie. kkk85 kkk8788"
            false
        }else if(db.duplicateUserNameFound(userName))
        {
            edtuserName.error = "userName is exists, please change the username."
            false
        } else {
            true
        }


    }

    fun emailValidation(): Boolean {
        val userEmail = edtEmail.text.toString()+textViewGmail.text.toString()
        val sPattern =
            Pattern.compile("(^[a-z])([a-z0-9]{4,})")//match the pattern insert in the shop name
        val match = sPattern.matcher(userEmail)
        return if (userEmail.isEmpty()) {
            edtEmail.error = "userEmail field cannot be empty"
            false
        }
        else if (!match.find())
        {
            edtEmail.error =
                "Invalid format.\n${resources.getString(R.string.bullet)}Small alphabet and number only and at least 5 character" +
                        "\n${resources.getString(R.string.bullet)}Recommend email:ie. kkk85 kkk_85"
            false
        }else if(db.duplicateEmailValidaton(userEmail))
        {
            edtEmail.error = "userEmail is exists, please change your email."
            false
        }
        else {
            true
        }
    }

    fun phoneValidation(): Boolean {
        val userPhone = edtPhoneNo.text.toString()
        val sPattern =
            Pattern.compile("[0][1][0-9]{8,9}")//match the pattern insert in the shop name
        val match = sPattern.matcher(userPhone)
        return if (userPhone.isNotEmpty() && match.find())
            true
        else if (userPhone.isEmpty()) {
            edtPhoneNo.error = "userEmail field cannot empty"
            false
        } else {
            edtPhoneNo.error =
                "Invalid phone number.\n${resources.getString(R.string.bullet)}ie. 0173380262"
            false
        }
    }

    fun userPasswordValidation(): Boolean {
        val userPassword = edtPassword.text.toString()
        val sPattern =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*.-]).{8,}\$")//match the pattern insert in the shop name
        val match = sPattern.matcher(userPassword)
        return if (userPassword.isNotEmpty() && match.find())
            true
        else if (userPassword.isEmpty()) {
            edtPassword.error = "Password field must at least 8 character"
            false
        } else {
            edtPassword.error =
                "Invalid format.\n${resources.getString(R.string.bullet)}must contain at least 8 character(at least one number,one special,one big cap,one small cap)" +
                        "\n${resources.getString(R.string.bullet)}Recommend email:ie. 12345aA@"
            false
        }

    }

    fun confirmPasswordValidation(): Boolean {
        val userPassword = edtPassword.text.toString()
        val userConfirmPassword = edtConfirmPassword.text.toString()
        return if (userPassword == userConfirmPassword) {
            true
        } else {
            edtConfirmPassword.error = "The password is not match with confirm password"
            false
        }

    }

    fun dialogBuilder() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Add Profile Confirmation")
            setMessage("Confirm to add new product profile?")
            setPositiveButton("Confirm") { dialog, which ->
                //insert into database
                insertData()
            }

            setNegativeButton("Discard") { dialog, which ->
                //clear all data
                clearDataConfirmation()
            }
            setNeutralButton("Cancel") { dialog, which ->
                //do nothing
                dialog.dismiss()
            }
            show()
        }
    }

    private fun insertData() {
        val userName = edtuserName.text.toString()
        val userEmail = edtEmail.text.toString()+ textViewGmail.text.toString()
        val userPhoneNo = edtPhoneNo.text.toString()
        val userPassword = edtPassword.text.toString()

        val rUser= RetailerUser(null,userName,userPassword,userEmail,userPhoneNo,null)
        val stmt = db.addUser(rUser)
        if (stmt != null) {
            Toast.makeText(
                this@retailer_registration,
                "The user is created",
                Toast.LENGTH_SHORT
            ).show()
            clearData()
            val i= Intent(this,retailer_login::class.java)
            startActivity(i)
        }
        else {
            Toast.makeText(
                this@retailer_registration,
                "The user is exists",
                Toast.LENGTH_SHORT
            ).show()
            edtuserName.error = "username exists,please insert another name"
        }
    }


    private fun clearDataConfirmation() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Clear Data")
            setMessage("Are you sure to clear all field?")
            setPositiveButton("Yes") { dialog, which ->
                clearData()
            }

            setNegativeButton("NO") { dialog, which ->
                //clear all data
                dialog.dismiss()
            }
            show()
        }

    }

    fun clearData() {
        edtConfirmPassword.setText("")
        edtPassword.setText("")
        edtPhoneNo.setText("")
        edtuserName.setText("")
        edtEmail.setText("")
    }
}