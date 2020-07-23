package com.example.laundryserviceapps.ClassModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class product_LaundryShopModelClass(
    var shopID:Int?,var shopName: String?, val establishDate: LocalDate?, val shopAddress: String?, val shopImage:ByteArray?,
    val shopStatus:String?,
    val contactPerson:String?,
    val phoneNo:String?,val retailerUser:String) {




    fun getEstablishDateTime(): String? {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        )

        return dateFormat.format(establishDate)
    }
     fun getImage(): Bitmap {
        val data=shopImage
        return BitmapFactory.decodeByteArray(data, 0, data!!.size)
    }
}
