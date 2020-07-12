package com.example.laundryserviceapps

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class product_LaundryShopModelClass(
    var shopID:Int?,var shopName: String?, val establishDate: LocalDate?, val shopAddress: String?, val shopImage:ByteArray?,
    val shopStatus:String?,
    val contactPerson:String?,
    val phoneNo:String?) {


    fun getEstablishDateTime(): String? {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        )

        return dateFormat.format(establishDate)
    }
}
