package com.example.laundryserviceapps.ClassModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderDetail (
    val order_no:Int = 0,
    val laundry_id: Int = 0,
    val quantity:Int = 0,
    val item_desc:String = "",
    val pickup_location:String = "",
    val delivery_location: String = "",
    val pickup_date:String = "",
    val delivery_date:String = ""): Parcelable