package com.example.laundryserviceapps.DatabaseHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.laundryserviceapps.ClassModel.Feedback
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.ClassModel.Promotion

class SQLiteHelper(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_CUSTOMER = "CREATE TABLE Customer (cust_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "first_name VARCHAR(20)," +
                "last_name VARCHAR(20),"  +
                "email VARCHAR(20)," +
                "phone_num BIGINT," +
                "account_mode VARCHAR(15)," +
                "password VARCHAR(20)," +
                "date_registered VARCHAR(60))"

        val CREATE_TABLE_ORDER = "CREATE TABLE Orders (order_no INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "order_date VARCHAR(50)," +
                "order_status VARCHAR(15)," +
                "payment_amt DECIMAL(20,2))"

        val CREATE_TABLE_LAUNDRY_SHOP = "CREATE TABLE LaundryShop (shopID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "laundry_shop_name TEXT," +
                "laundry_shop_Address TEXT, " +
                "shop_date_establish TEXT, " +
                "shop_status TEXT," +
                "contact_person TEXT," +
                "phone_number TEXT)"

        val CREATE_SHOP_PROMOTION = ("CREATE TABLE IF NOT EXISTS Promotion( " +
                "promoID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "promo_name TEXT, " +
                "discount TEXT, " +
                "dateexpired TEXT)")

        val CREATE_TABLE_STAFF = "CREATE TABLE Staff (staff_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "s_first_name TEXT," +
                "s_last_name TEXT,"  +
                "s_email TEXT," +
                "s_phone_num BIGINT," +
                "s_account_mode TEXT," +
                "s_password VARCHAR(20)," +
                "s_date_registered TEXT)"

        val CREATE_TABLE_FEEDBACK = "CREATE TABLE Feedback (feedback_no INTEGER PRIMARY KEY AUTOINCREMENT," +
                "feedback_rate DOUBLE," +
                "comment TEXT," +
                "post_date TEXT)"

        db?.execSQL(CREATE_TABLE_CUSTOMER)
        db?.execSQL(CREATE_TABLE_ORDER)
        db?.execSQL(CREATE_TABLE_LAUNDRY_SHOP)

        db?.execSQL(CREATE_TABLE_STAFF)
        db?.execSQL(CREATE_SHOP_PROMOTION)
        db?.execSQL(CREATE_TABLE_FEEDBACK)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_CUSTOMER = "DROP TABLE IF EXISTS Customer"
        val DROP_TABLE_ORDER = "DROP TABLE IF EXISTS Orders"
        val DROP_TABLE_LAUNDRY_SHOP = "DROP TABLE IF EXISTS LaundryShop"

        val CREATE_TABLE_FEEDBACK = "DROP TABLE IF EXISTS Feedback"
        val CREATE_TABLE_STAFF = "DROP TABLE IF EXISTS Staff"
        val CREATE_SHOP_PROMOTION = "DROP TABLE IF EXISTS Promotion"

        db!!.execSQL(DROP_TABLE_CUSTOMER)
        db!!.execSQL(DROP_TABLE_ORDER)
        db!!.execSQL(DROP_TABLE_LAUNDRY_SHOP)

        db!!.execSQL(CREATE_TABLE_FEEDBACK)
        db!!.execSQL(CREATE_TABLE_STAFF)
        db!!.execSQL(CREATE_SHOP_PROMOTION)
    }

    // ========================================================================================

    fun onDropLaundryShop()
    {
        val db=this.writableDatabase
        val DROP_TABLE_LAUNDRY_SHOP = "DROP TABLE IF EXISTS LaundryShop"
        db!!.execSQL(DROP_TABLE_LAUNDRY_SHOP)
    }

    fun onCreateLaundryShop()
    {
        val db=this.writableDatabase
        Log.i("databaseOnCreate","it is created")
        val CREATE_TABLE_LAUNDRY_SHOP = "CREATE TABLE LaundryShop (shopID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "laundry_shop_name TEXT," +
                "laundry_shop_Address TEXT, " +
                "shop_date_establish TEXT, " +
                "shop_status TEXT," +
                "contact_person TEXT," +
                "phone_number TEXT)"

        db?.execSQL(CREATE_TABLE_LAUNDRY_SHOP)
    }

    fun addLaundryShop(laundryshop: Laundry_Shop){
        val db: SQLiteDatabase = writableDatabase
        val values  = ContentValues()
        values.put("laundry_shop_name",laundryshop.laundry_shop_name)
        values.put("laundry_shop_Address",laundryshop.laundry_shop_Address)
        values.put("shop_date_establish",laundryshop.datecreated)
        values.put("shop_status",laundryshop.shop_status)
        values.put("contact_person",laundryshop.contact_person)
        values.put("phone_number",laundryshop.phone_number)
        db.insert("LaundryShop",null,values)
        db.close()
    }

    fun getLaundryShop():ArrayList<Laundry_Shop>{
        val lstLS:ArrayList<Laundry_Shop> = ArrayList()
        val query = "SELECT * FROM LaundryShop"
        val db = this.readableDatabase
        var cursor: Cursor

        try{
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException) {
            db.execSQL(query)
            return ArrayList()
        }
        var shopID: Int
        var laundry_shop_name: String
        var laundry_shop_Address: String
        var shop_date_establish: String
        var shop_status: String
        var contact_person: String
        var phone_number: String

        if (cursor.moveToFirst()) {
            do {
                shopID = cursor.getInt(cursor.getColumnIndex("shopID"))
                laundry_shop_name = cursor.getString(cursor.getColumnIndex("laundry_shop_name"))
                laundry_shop_Address = cursor.getString(cursor.getColumnIndex("laundry_shop_Address"))
                shop_date_establish = cursor.getString(cursor.getColumnIndex("shop_date_establish"))
                shop_status = cursor.getString(cursor.getColumnIndex("shop_status"))
                contact_person = cursor.getString(cursor.getColumnIndex("contact_person"))
                phone_number = cursor.getString(cursor.getColumnIndex("phone_number"))
                val lstlaundryshop = Laundry_Shop(shopID = shopID ,laundry_shop_name = laundry_shop_name,laundry_shop_Address = laundry_shop_Address,
                    shop_status = shop_status,contact_person =  contact_person, datecreated = shop_date_establish, phone_number = phone_number)
                lstLS.add(lstlaundryshop)

            } while (cursor.moveToNext())
        }
        return lstLS
    }

//    fun getLaundryShopListByKeyword(search: String): Cursor? {
//        //Open connection to read only
//        val db = this.readableDatabase
//        val selectQuery = "SELECT laundry_shop_name," +
//                " laundry_shop_Address" +
//                " FROM LaundryShop" +
//                " WHERE laundry_shop_Address LIKE '%" + search + "%' "
//        val cursor = db.rawQuery(selectQuery, null)
//        // looping through all rows and adding to list
//        if (cursor == null) {
//            return null
//        } else if (!cursor.moveToFirst()) {
//            cursor.close()
//            return null
//        }
//        return cursor
//    }

    fun updateLaundryShopStatus(laundryshop: Laundry_Shop) {
        val db: SQLiteDatabase = writableDatabase
        val query = "UPDATE LaundryShop SET shop_status='${laundryshop.shop_status}' WHERE shopID = ${laundryshop.shopID}"
        db.execSQL(query)
    }
    // ========================================================================================

    fun onDropFeedback()
    {
        val db=this.writableDatabase
        val CREATE_TABLE_FEEDBACK = "DROP TABLE IF EXISTS Feedback"
        db?.execSQL(CREATE_TABLE_FEEDBACK)
    }

    fun onCreateFeedback()
    {
        val db=this.writableDatabase
        Log.i("databaseOnCreate","it is created")
        val CREATE_TABLE_FEEDBACK = "CREATE TABLE Feedback (feedback_no INTEGER PRIMARY KEY AUTOINCREMENT," +
                "feedback_rate DOUBLE," +
                "comment TEXT," +
                "post_date TEXT)"

        db?.execSQL(CREATE_TABLE_FEEDBACK)
    }

    fun postFeedback(feedback: Feedback){
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues  = ContentValues()
        values.put("feedback_rate",feedback.feedback_rate)
        values.put("comment",feedback.comment)
        values.put("post_date",feedback.post_date)
        db.insert("Feedback",null,values)
        db.close()
    }


    fun getFeedback():ArrayList<Feedback>{
        val query = "SELECT * FROM Feedback"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query,null)
        val lstfeedback = ArrayList<Feedback>()

        if(cursor.moveToFirst()){
            do {
                val feedback = Feedback()
                feedback.feedback_no = cursor.getInt(cursor.getColumnIndex("feedback_no"))
                feedback.feedback_rate = cursor.getDouble(cursor.getColumnIndex("feedback_rate"))
                feedback.comment = cursor.getString(cursor.getColumnIndex("comment"))
                feedback.post_date = cursor.getString(cursor.getColumnIndex("post_date"))
                lstfeedback.add(feedback)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lstfeedback
    }

    // ========================================================================================

    fun onCreatePromotion()
    {
        val db=this.writableDatabase
        Log.i("databaseOnCreate","it is created")
        val CREATE_SHOP_PROMOTION = ("CREATE TABLE IF NOT EXISTS Promotion( " +
                "promoID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "promo_name TEXT, " +
                "discount TEXT, " +
                "dateexpired TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL)")

        db?.execSQL(CREATE_SHOP_PROMOTION)
    }

    fun addPromotion(promotion: Promotion){
        val db: SQLiteDatabase = writableDatabase
        val values  = ContentValues()
        values.put("promo_name",promotion.promo_name)
        values.put("discount",promotion.discount) //
        db.insert("Promotion",null,values)
        db.close()
    }

    fun getPromo():List<Promotion>{
        val lstpromo:ArrayList<Promotion> = ArrayList()
        val query = "SELECT * FROM Promotion"
        val db = this.readableDatabase
        var cursor: Cursor

        try{
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException) {
            db.execSQL(query)
            return ArrayList()
        }
        var promoid: Int
        var promoname: String
        var discount: Double
        var dateexpired: String

        if (cursor.moveToFirst()) {
            do {
                promoid = cursor.getInt(cursor.getColumnIndex("promoID"))
                promoname = cursor.getString(cursor.getColumnIndex("promo_name"))
                discount = cursor.getDouble(cursor.getColumnIndex("discount"))
                dateexpired = cursor.getString(cursor.getColumnIndex("dateexpired"))
                val lpromotion= Promotion(promoID = promoid,promo_name = promoname,discount = discount,dateexpired = dateexpired)
                lstpromo.add(lpromotion)

            } while (cursor.moveToNext())
        }
        return lstpromo
    }

    // ========================================================================================

    fun onCreateStaff()
    {
        val db=this.writableDatabase
        Log.i("databaseOnCreate","it is created")
        val CREATE_TABLE_STAFF = "CREATE TABLE Staff (staff_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "s_first_name TEXT," +
                "s_last_name TEXT,"  +
                "s_email TEXT," +
                "s_phone_num BIGINT," +
                "s_account_mode TEXT," +
                "s_password VARCHAR(20)," +
                "s_date_registered TEXT)"

        db?.execSQL(CREATE_TABLE_STAFF)
    }


    fun insertStaffData(s_first_name: String, s_last_name: String, s_email: String, s_phone_num: Long,
                        s_account_mode: String, s_password: String, s_date_registered: String){
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues  = ContentValues()
        values.put("s_first_name",s_first_name)
        values.put("s_last_name",s_last_name)
        values.put("s_email",s_email)
        values.put("s_phone_num",s_phone_num)
        values.put("s_account_mode",s_account_mode)
        values.put("s_password",s_password)
        values.put("s_date_registered",s_date_registered)

        db.insert("Staff",null,values)
        db.close()
    }

    fun checkStaffUser(s_email: String, s_password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM Staff WHERE s_email='$s_email' AND s_password='$s_password'"
        val cursor: Cursor = db.rawQuery(query,null)
        if(cursor.count<=0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }


    fun changeStaffPassword(s_email: String, s_password: String): Boolean {
        val db = writableDatabase
        val values: ContentValues  = ContentValues()
        values.put("s_password",s_password)
        db.update("Staff",values,"s_email = ?",arrayOf(s_email))
        return true
    }

   // ========================================================================================

    fun insertCustomerData(first_name: String, last_name: String, email: String, phone_num: Long,
                           account_mode: String, password: String, date_registered: String){
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues  = ContentValues()
        values.put("first_name",first_name)
        values.put("last_name",last_name)
        values.put("email",email)
        values.put("phone_num",phone_num)
        values.put("account_mode",account_mode)
        values.put("password",password)
        values.put("date_registered",date_registered)

        db.insert("Customer",null,values)
        db.close()
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM Customer WHERE email='$email' AND password='$password'"
        val cursor: Cursor = db.rawQuery(query,null)
        if(cursor.count<=0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }


    fun changePassword(email: String, password: String): Boolean {
        val db = writableDatabase
        val values: ContentValues  = ContentValues()
        values.put("password",password)
        db.update("Customer",values,"email = ?",arrayOf(email))
        return true
    }

    // ========================================================================================

    fun addOrder(order:Order){
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues  = ContentValues()
        values.put("order_date",order.order_date)
        values.put("order_status",order.order_status)
        values.put("payment_amt",order.payment_amt)

        db.insert("Orders",null,values)
        db.close()
    }

    fun getOrderList():ArrayList<Order>{
        val query = "SELECT * FROM Orders"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query,null)
        val orderlist = ArrayList<Order>()

        if(cursor.moveToFirst()){
            do {
                val order = Order()
                order.order_no = cursor.getInt(cursor.getColumnIndex("order_no"))
                order.order_date = cursor.getString(cursor.getColumnIndex("order_date"))
                order.order_status = cursor.getString(cursor.getColumnIndex("order_status"))
                order.payment_amt = cursor.getDouble(cursor.getColumnIndex("payment_amt"))
                orderlist.add(order)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return orderlist
    }

    fun getOrderHistoryList():ArrayList<Order>{
        val query = "SELECT order_no, order_date, payment_amt FROM Orders WHERE order_status = 'Completed'"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query,null)
        val orderlist = ArrayList<Order>()

        if(cursor.moveToFirst()){
            do {
                val order = Order()
                order.order_no = cursor.getInt(cursor.getColumnIndex("order_no"))
                order.order_date = cursor.getString(cursor.getColumnIndex("order_date"))
                order.payment_amt = cursor.getDouble(cursor.getColumnIndex("payment_amt"))
                orderlist.add(order)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return orderlist
    }

    fun updateOrderStatus(order: Order) {
        val db: SQLiteDatabase = writableDatabase
        val query = "UPDATE Orders SET order_status='${order.order_status}' WHERE order_no = ${order.order_no}"
        db.execSQL(query)
    }

    // ========================================================================================


    // ========================================================================================


    // ========================================================================================

    companion object{
        internal val DATABASE_NAME = "LaundryServiceDB.db"
        internal val DATABASE_VERSION = 1
    }

}

