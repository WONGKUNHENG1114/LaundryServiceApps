package com.example.laundryserviceapps.DatabaseHandler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.laundryserviceapps.ClassModel.product_LaundryShopModelClass
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class product_databaseHandler(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "LaundryServiceDB.db"
        const val TABLE_CONTACTS = "LaundryShop"
        const val SHOP_ID = "ShopID"
        const val SHOP_NAME = "ShopName"
        const val SHOP_ESTABLISH_DATE = "EstablishDate"
        const val SHOP_ADDRESS = "ShopAddress"
        const val SHOP_IMAGE = "ShopImage"
        const val SHOP_STATUS = "Status"
        const val CONTACT_PERSON = "ContactPerson"
        const val PHONE_NUMBER = "PhoneNumber"
        const val RETAILER_USER = "username"
    }
    //*****SQLITE******
    //LaundryShop
//    ShopID INTEGER
//    ShopName TEXT
//    EstablishDate NUMERIC
//    ShopAddress TEXT
//    ContactPerson TEXT
//    PhoneNumber  TEXT

    override fun onCreate(db: SQLiteDatabase?) {
        val ACTIVE_FOREIGN_KEY = "PRAGMA foreign_keys = ON;"
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS  " + TABLE_CONTACTS + "("
                + SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SHOP_NAME + " TEXT,"
                + SHOP_ESTABLISH_DATE + " TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL,"
                + SHOP_ADDRESS + " TEXT,"
                + SHOP_IMAGE + " BLOB,"
                + SHOP_STATUS + " TEXT DEFAULT 'Active',"
                + CONTACT_PERSON + " TEXT,"
                + PHONE_NUMBER + " TEXT,"
                + RETAILER_USER + " TEXT NOT NULL,"
                + "FOREIGN KEY($RETAILER_USER) REFERENCES userRetailer($RETAILER_USER);")

        db?.execSQL(CREATE_CONTACTS_TABLE)
        db?.execSQL(ACTIVE_FOREIGN_KEY)
    }

    fun onCreateLaundryShop() {
        val db = this.writableDatabase
        Log.i("databaseOnCreate", "it is created")
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS  " + TABLE_CONTACTS + "("
                + SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SHOP_NAME + " TEXT,"
                + SHOP_ESTABLISH_DATE + " TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL,"
                + SHOP_ADDRESS + " TEXT,"
                + SHOP_IMAGE + " BLOB,"
                + SHOP_STATUS + " TEXT DEFAULT 'Active',"
                + CONTACT_PERSON + " TEXT,"
                + PHONE_NUMBER + " TEXT)")

        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("databaseOnUpgrade", "it is upgrade")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addLaundryShop(lShop: product_LaundryShopModelClass): Long? {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        val damn = duplicateLaundryNameFound(lShop.shopName)
        Log.i("damn", damn.toString())
        if (!damn) {
            contentValues.put(SHOP_NAME, lShop.shopName)//LaundryShopModel shopName
            // contentValues.put(SHOP_ESTABLISH_DATE, lShop.getEstablishDateTime()) //LaundryShopModel EstablishedDate
            contentValues.put(SHOP_ADDRESS, lShop.shopAddress) //LaundryShopModel shopAddress
            contentValues.put(SHOP_IMAGE, lShop.shopImage) //LaundryShopModel shopAddress
            //contentValues.put(SHOP_STATUS,lShop.shopStatus) //LaundryShopModel shopAddress
            contentValues.put(CONTACT_PERSON, lShop.contactPerson) //LaundryShopModel shopAddress
            contentValues.put(PHONE_NUMBER, lShop.phoneNo) //LaundryShopModel shopAddress
            contentValues.put(RETAILER_USER, lShop.retailerUser) //LaundryShopModel shopAddress
            // Inserting Row
            val success = db.insert(TABLE_CONTACTS, null, contentValues)
            //2nd argument is String containing nullColumnHack
            db.close() // Closing database connection
            return success
        } else

            return null
    }

    //method to read data

    @RequiresApi(Build.VERSION_CODES.O)
    fun viewShopLaundry(username: String): List<product_LaundryShopModelClass> {

        val laundryShopList: ArrayList<product_LaundryShopModelClass> = ArrayList()
        val selectQuery =
            "SELECT  * FROM $TABLE_CONTACTS where $RETAILER_USER='$username' AND $SHOP_STATUS='Active'"
        val db = this.readableDatabase
        var cursor: Cursor

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var ShopID: Int
        var ShopName: String
        var ShopEstablishDate: String
        var ShopAddress: String
        var ShopImage: ByteArray
        var ShopStatus: String
        var ShopContact_Person: String
        var ShopPhoneNo: String
        var shopRetailerUser: String
        if (cursor.moveToFirst()) {
//            try{


            do {
                ShopID = cursor.getInt(cursor.getColumnIndex(SHOP_ID))
                ShopName = cursor.getString(cursor.getColumnIndex(SHOP_NAME))
                ShopEstablishDate = cursor.getString(cursor.getColumnIndex(SHOP_ESTABLISH_DATE))
                ShopAddress = cursor.getString(cursor.getColumnIndex(SHOP_ADDRESS))
                ShopImage = cursor.getBlob(cursor.getColumnIndex(SHOP_IMAGE))
                ShopStatus = cursor.getString(cursor.getColumnIndex(SHOP_STATUS))
                ShopContact_Person = cursor.getString(cursor.getColumnIndex(CONTACT_PERSON))
                ShopPhoneNo = cursor.getString(cursor.getColumnIndex(PHONE_NUMBER))
                shopRetailerUser = cursor.getString(cursor.getColumnIndex(RETAILER_USER))
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val lShop =
                    product_LaundryShopModelClass(
                        shopID = ShopID, shopName = ShopName,
                        establishDate = LocalDate.parse(ShopEstablishDate, formatter),
                        shopAddress = ShopAddress,
                        shopImage = ShopImage,
                        shopStatus = ShopStatus,
                        contactPerson = ShopContact_Person,
                        phoneNo = ShopPhoneNo,
                        retailerUser = shopRetailerUser
                    )

                laundryShopList.add(lShop)

            } while (cursor.moveToNext())
        }

        return laundryShopList
    }


    fun duplicateLaundryNameFound(lShop: String?): Boolean {

        val selectQuery =
            "SELECT  * FROM $TABLE_CONTACTS  WHERE ShopName ='$lShop' AND $SHOP_STATUS='Active' LIMIT 1"
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

    //method to update data
    fun updateLaundry(lShop: product_LaundryShopModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SHOP_ID, lShop.shopID)
        contentValues.put(SHOP_NAME, lShop.shopName) // EmpModelClass Name
        contentValues.put(SHOP_ADDRESS, lShop.shopAddress) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues, "shopName=" + lShop.shopName, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to delete data
    fun deleteEmployee(lShop: product_LaundryShopModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SHOP_ID, lShop.shopID) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, "id=" + lShop.shopID, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}