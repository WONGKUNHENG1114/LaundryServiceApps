package com.example.laundryserviceapps.ClassModel

import android.provider.BaseColumns
import kotlinx.android.parcel.Parcelize


class Order (
    var order_no:Int = 0,
    var order_date:String = "",
    var order_status: String ="",
    var payment_amt:Double = 0.0)

