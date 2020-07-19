package com.example.laundryserviceapps

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.retailer_user_registration.*
import java.security.Key
import java.security.Security
import java.util.regex.Pattern
import javax.crypto.Cipher


class retailer_registration : AppCompatActivity() {
    private var outputString:String= ""
    private val KEY="testpassword"
    private var usernameValid:Boolean = false
    private var emailValid:Boolean = false
    private var phoneValid:Boolean = false
    private var userPasswordValid :Boolean = false
    private var confirmPasswordValid :Boolean = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailer_user_registration)
        btnRegister.setOnClickListener {
            register_user()
        }

        scrollViewId.setOnTouchListener { v, _ ->
            hideSoftKeyboard(v)
            false
        }

        edtuserName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                usernameValid = userNameValidation()
            }
        }

        edtEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                emailValid = emailValidation()
            }
        }

        edtPhoneNo.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                phoneValid = phoneValidation()
            }
        }
        edtPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                userPasswordValid = userPasswordValidation()
            }
        }
        edtConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                confirmPasswordValid =  confirmPasswordValidation()
            }
        }

    }
    private fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun register_user() {
        val password=edtPassword.text.toString()
        outputString=encrypt(password,KEY)

    }

    fun userNameValidation():Boolean {
        val userName = edtuserName.text.toString()
        val sPattern =
            Pattern.compile("(^[a-z])([a-z0-9]{4,})")//match the pattern insert in the shop name
        val match = sPattern.matcher(userName)
        return if(userName.isNotEmpty() && match.find())
            true
        else if (userName.isEmpty()) {
            edtuserName.error="username cannot empty"
            false
        } else {
            edtuserName.error="Invalid format.\n${resources.getString(R.string.bullet)}Small alphabet and number only and at least 5 character" +
                    "\n${resources.getString(R.string.bullet)}Recommend username:ie. kkk85 kkk_85"
            false
        }



    }

    fun emailValidation(): Boolean {
        val userEmail = edtEmail.text.toString()
        val sPattern =
            Pattern.compile("(^[a-z])([a-z0-9]{4,})")//match the pattern insert in the shop name
        val match = sPattern.matcher(userEmail)
        return if(userEmail.isNotEmpty() && match.find())
            true
        else if (userEmail.isEmpty()) {
            edtEmail.error="userEmail field cannot empty"
            false
        } else {
            edtEmail.error="Invalid format.\n${resources.getString(R.string.bullet)}Small alphabet and number only and at least 5 character" +
                    "\n${resources.getString(R.string.bullet)}Recommend email:ie. kkk85 kkk_85"
            false
        }
    }

    fun phoneValidation():Boolean {
        val userPhone = edtPhoneNo.text.toString()
        val sPattern =
            Pattern.compile("[0][1][0-9]{8,9}")//match the pattern insert in the shop name
        val match = sPattern.matcher(userPhone)
        return if(userPhone.isNotEmpty() && match.find())
            true
        else if (userPhone.isEmpty()) {
            edtPhoneNo.error="userEmail field cannot empty"
            false
        } else {
            edtPhoneNo.error="Invalid phone number.\n${resources.getString(R.string.bullet)}ie. 0173380262"
            false
        }
    }

    fun userPasswordValidation():Boolean {
        val userPassword = edtPassword.text.toString()
        val sPattern =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*.-]).{8,}\$")//match the pattern insert in the shop name
        val match = sPattern.matcher(userPassword)
        return if(userPassword.isNotEmpty() && match.find())
            true
        else if (userPassword.isEmpty()) {
            edtPassword.error="Password field must at least 8 character"
            false
        } else {
            edtPassword.error="Invalid format.\n${resources.getString(R.string.bullet)}must contain at least 8 character(at least one number,one special,one big cap,one small cap)" +
                    "\n${resources.getString(R.string.bullet)}Recommend email:ie. 12345aA@"
            false
        }

    }

    fun confirmPasswordValidation():Boolean {
        val userPassword = edtPassword.text.toString()
        val userConfirmPassword = edtConfirmPassword.text.toString()
        return if(userPassword == userConfirmPassword) {
            true
        } else
        {
            edtConfirmPassword.error="The password is not match with confirm password"
            false
        }

    }

//    @Throws(Exception::class)
//    fun encrypt(data: String, secret_key: String): String {
//        Security.addProvider(BouncyCastleProvider())
//        val key: Key = generateKey()
//        val cipher: Cipher = Cipher.getInstance(AESCrypt.ALGORITHM)
//        cipher.init(Cipher.ENCRYPT_MODE, key)
//        val encryptedByteValue: ByteArray = cipher.doFinal(value.toByteArray(charset("utf-8")))
//        return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)
//    }
}