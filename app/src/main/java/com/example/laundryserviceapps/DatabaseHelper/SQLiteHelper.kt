package com.example.laundryserviceapps.DatabaseHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.laundryserviceapps.ClassModel.Order

class SQLiteHelper(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

//        val ORDER_DETAIL_TABLE = "Order_Detail"
//        val QUANTITY = "quantity"
//        val ITEM_DESC = "item_desc"
//        val PICKUP_LOCATION = "pickup_location"
//        val DELIVERY_LOCATION = "delivery_location"
//        val PICKUP_DATE = "pickup_date"
//        val DELIVERY_DATE = "delivery_date"

//    val ORDER_TABLE = "Orders"
//    val ORDER_NO = "order_no"
//    val ORDER_DATE = "order_date"
//    val ORDER_STATUS = "order_status"
//    val PAYMENT_AMOUNT = "payment_amt"

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

        db?.execSQL(CREATE_TABLE_CUSTOMER)
        db?.execSQL(CREATE_TABLE_ORDER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_CUSTOMER = "DROP TABLE IF EXISTS Customer"
        val DROP_TABLE_ORDER = "DROP TABLE IF EXISTS Orders"

        db!!.execSQL(DROP_TABLE_CUSTOMER)
        db!!.execSQL(DROP_TABLE_ORDER)
    }

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

//    fun getOrderNo():ArrayList<Order>{
//        val query = "SELECT last_insert_rowid() FROM Orders"
//        val db = this.readableDatabase
//        val cursor: Cursor = db.rawQuery(query,null)
//        val orderlist = ArrayList<Order>()
//
//        if(cursor.moveToFirst()){
//            do {
//                val order = Order()
//                order.order_no = cursor.getInt(cursor.getColumnIndex("order_no"))
//                orderlist.add(order)
//            }while (cursor.moveToNext())
//        }
//        cursor.close()
//        db.close()
//        return orderlist
//    }

    fun updateOrderStatus(order: Order) {
        val db: SQLiteDatabase = writableDatabase
        val query = "UPDATE Orders SET order_status='${order.order_status}' WHERE order_no = ${order.order_no}"
        db.execSQL(query)
    }

    companion object{
        internal val DATABASE_NAME = "LaundryServiceDB.db"
        internal val DATABASE_VERSION = 1
    }

}

