package com.example.laundryserviceapps.DatabaseHandler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.laundryserviceapps.ClassModel.RetailerUser
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class retailer_databaseHandler(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "LaundryServiceDB.db"
        const val TABLE_CONTACTS = "userRetailer"
        const val USER_NAME = "username"
        const val PASSWORD = "password"
        const val TEL_NO = "phoneNo"
        const val EMAIL = "email"
        const val USER_STATUS = "status"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        var stmt=""
        val ACTIVE_FOREIGN_KEY="PRAGMA foreign_keys = ON;"
        val CREATE_PRODUCT_TABLE = ("CREATE TABLE IF NOT EXISTS  " + product_databaseHandler.TABLE_CONTACTS + "("
                + product_databaseHandler.SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + product_databaseHandler.SHOP_NAME + " TEXT,"
                + product_databaseHandler.SHOP_ESTABLISH_DATE + " TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL,"
                + product_databaseHandler.SHOP_ADDRESS +" TEXT,"
                + product_databaseHandler.SHOP_IMAGE +" BLOB,"
                + product_databaseHandler.SHOP_STATUS +" TEXT DEFAULT 'Active',"
                + product_databaseHandler.CONTACT_PERSON +" TEXT,"
                + product_databaseHandler.PHONE_NUMBER +" TEXT,"
                + product_databaseHandler.RETAILER_USER + " TEXT NOT NULL,"
                + "FOREIGN KEY(${product_databaseHandler.RETAILER_USER}) REFERENCES $TABLE_CONTACTS(${product_databaseHandler.RETAILER_USER}));")


        val CREATE_RETAILER_USER_TABLE = ("CREATE TABLE IF NOT EXISTS  " + TABLE_CONTACTS + "("
                + USER_NAME + " TEXT PRIMARY KEY,"
                + PASSWORD + " TEXT,"
                + EMAIL + " TEXT,"
                + TEL_NO + " TEXT,"
                + USER_STATUS + " TEXT DEFAULT 'Active');")
        db?.execSQL(CREATE_RETAILER_USER_TABLE)
        db?.execSQL(CREATE_PRODUCT_TABLE)
        db?.execSQL(ACTIVE_FOREIGN_KEY)

    }

    fun onCreateRetailerUser() {
        val db = this.writableDatabase
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS  " + TABLE_CONTACTS + "("
                + USER_NAME + " TEXT PRIMARY KEY,"
                + PASSWORD + " TEXT,"
                + EMAIL + " TEXT,"
                + TEL_NO + " TEXT,"
                + USER_STATUS + " TEXT DEFAULT 'Active')")

        db?.execSQL(CREATE_CONTACTS_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addUser(rUser: RetailerUser): Long? {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        val duplicateUserNameFound = duplicateUserNameFound(rUser.username)
        Log.i("damn", duplicateUserNameFound.toString())
        return if (!duplicateUserNameFound) {
            contentValues.put(USER_NAME, rUser.username) //LaundryShopModel username
            contentValues.put(PASSWORD, rUser.userPassword) //LaundryShopModel userPassword
            contentValues.put(EMAIL, rUser.phoneNo) //LaundryShopModel phoneNo
            contentValues.put(TEL_NO, rUser.userEmail) //LaundryShopModel userEmail

            // Inserting Row
            val success = db.insert(TABLE_CONTACTS, null, contentValues)
            //2nd argument is String containing nullColumnHack
            db.close() // Closing database connection
            success
        } else {

            null
        }

    }

    fun MatchUserProfile(username: String, password: String): Boolean {

        val selectQuery =
            "SELECT  * FROM $TABLE_CONTACTS WHERE $USER_NAME='$username' and $PASSWORD='$password' and $USER_STATUS='Active' "
        val db = this.readableDatabase
        var cursor: Cursor
        Log.i("username",username)

        try {
            cursor = db.rawQuery(selectQuery, null)
            Log.i("username",cursor.count.toString())
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return false
        }

        return cursor.count > 0


    }

    fun duplicateUserNameFound(user: String?): Boolean {

        val selectQuery =
            "SELECT  * FROM $TABLE_CONTACTS  WHERE $USER_NAME ='$user' and $USER_STATUS='Active' LIMIT 1"
        val db = this.readableDatabase
        val cursor: Cursor
        cursor = db.rawQuery(selectQuery, null)
        return try {
            if (cursor.count < 1) {
                Log.i("cursorState", cursor.count.toString())
                false
            } else {
                true
            }
        } catch (e: SQLiteException) {
            Log.i("hello", e.toString())
            db.execSQL(selectQuery)
            false
        }

    }
    fun duplicateEmailValidaton(email:String?):Boolean
    {
        val selectQuery =
            "SELECT  * FROM $TABLE_CONTACTS  WHERE $EMAIL ='$email' and $USER_STATUS='Active' LIMIT 1"
        val db = this.readableDatabase
        val cursor: Cursor
        cursor = db.rawQuery(selectQuery, null)
        return try {
            if (cursor.count < 1) {
                Log.i("cursorState", cursor.count.toString())
                false
            } else {
                true
            }
        } catch (e: SQLiteException) {
            Log.i("hello", e.toString())
            db.execSQL(selectQuery)
            false
        }
    }
}