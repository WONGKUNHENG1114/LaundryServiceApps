package com.example.laundryserviceapps.DatabaseHandler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.laundryserviceapps.ClassModel.RetailerUser

class retailer_databaseHandler(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object {

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "LaundryServiceDB.db"
        const val TABLE_CONTACTS = "userRetailer"
        const val USER_ID = "userID"
        const val USER_NAME = "username"
        const val PASSWORD = "password"
        const val TEL_NO = "phoneNo"
        const val EMAIL="email"
        const val USER_STATUS="status"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS  " + TABLE_CONTACTS + "("
                + USER_ID + " INTEGER PRIMARY KEY,"
                + USER_NAME + " TEXT,"
                + PASSWORD +" TEXT,"
                + EMAIL +" TEXT,"
                + TEL_NO +" TEXT,"
                + USER_STATUS +" TEXT DEFAULT 'Active')")

        db?.execSQL(CREATE_CONTACTS_TABLE)
    }
    fun onCreateRetailerUser()
    {
        val db=this.writableDatabase
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS  " + TABLE_CONTACTS + "("
                + USER_ID + " INTEGER PRIMARY KEY,"
                + USER_NAME + " TEXT,"
                + PASSWORD +" TEXT,"
                + EMAIL +" TEXT,"
                + TEL_NO +" TEXT,"
                + USER_STATUS +" TEXT DEFAULT 'Active')")

        db?.execSQL(CREATE_CONTACTS_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun addUser(rUser: RetailerUser): Long?
    {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        val duplicateFound= duplicateUserNameFound(rUser.username)
        Log.i("damn",duplicateFound.toString())
        if(!duplicateFound) {
            contentValues.put(USER_ID, rUser.userID)//LaundryShopModel shopName
            contentValues.put(USER_NAME, rUser.username) //LaundryShopModel username
            contentValues.put(PASSWORD, rUser.userPassword) //LaundryShopModel userPassword
            contentValues.put(EMAIL, rUser.phoneNo) //LaundryShopModel phoneNo
            contentValues.put(TEL_NO, rUser.userEmail) //LaundryShopModel userEmail
            contentValues.put(USER_STATUS, rUser.status) //LaundryShopModel status
            // Inserting Row
            val success = db.insert(product_databaseHandler.TABLE_CONTACTS, null, contentValues)
            //2nd argument is String containing nullColumnHack
            db.close() // Closing database connection
            return success
        }
        else

            return null
    }


    fun duplicateUserNameFound(user: String?):Boolean
    {

        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS  WHERE $USER_NAME ='$user' LIMIT 1"
        val db = this.readableDatabase
        val cursor: Cursor
        cursor = db.rawQuery(selectQuery, null)
        return try{
            if(cursor.count<1) {
                Log.i("cursorState",cursor.count.toString())
                false
            } else {

                true
            }
        }catch (e: SQLiteException) {
            Log.i("hello",e.toString())
            db.execSQL(selectQuery)
            false
        }

    }
}