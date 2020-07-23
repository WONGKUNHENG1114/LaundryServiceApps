package com.example.laundryserviceapps

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.icu.lang.UCharacter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.RemoteViews
import android.widget.Toast
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_delivery_address.*
import kotlinx.android.synthetic.main.activity_pickup_address.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_review_order.*
import kotlinx.android.synthetic.main.activity_select_service.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ReviewOrder : AppCompatActivity() {

    lateinit var handler: SQLiteHelper
    private val STORAGE_CODE: Int = 100;

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "com.example.laundryserviceapps"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_order)

        handler = SQLiteHelper(this)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        lbl_date_order.text = formatedDate.toString()
        display_order()

        btnprevious.setOnClickListener {
            onBackPressed()
        }

        btnConfirmOrder.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                }
                else{
                    save_Pdf_file()
                }
            }
            else{
                save_Pdf_file()
            }



            handler.addOrder(order = Order(0,lbl_date_order.text.toString(),"Pending",lbl_payment_amy_ro.text.toString().toDouble()))
            val intent = Intent(this,OrderSuccess::class.java)

            val pendingIntent = PendingIntent.getActivity(this,
                0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(
                    channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,channelId)
                    .setContentTitle("Book Laundry Service")
                    .setContentText("Your order successfully placed, please check your order tracking")
                    .setSmallIcon(R.drawable.group_26)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(this.resources,
                            R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            }else{

                builder = Notification.Builder(this)
                    .setContentTitle("Book Laundry Service")
                    .setContentText("Your order successfully placed, please check your order tracking")
                    .setSmallIcon(R.drawable.group_26)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(this.resources,
                            R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())

            intent.putExtra("Shop_name_feedback",lbl_getshopname4.text.toString())
            intent.putExtra("Shop_address_feedback",lbl_getshopaddress4.text.toString())

            startActivity(intent)
            finish()
        }
    }

    private fun save_Pdf_file() {

        val myDoc = Document()
        val myFileName = "Order Receipt"
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        lbl_date_order.text = formatedDate.toString()

        val myFilePath = Environment.getExternalStorageDirectory().toString() + "/" + myFileName + " " + lbl_date_order.text.toString() + ".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(myDoc, FileOutputStream(myFilePath))

            //open the document for writing
            myDoc.open()

            val get_pickup_address = lblget_address_pickup.text.toString()
            val get_pickup_time = lblget_time_pickup.text.toString()
            val get_pickup_date = lblget_date_pickup.text.toString()

            val get_delivery_address = lblget_address_delivery.text.toString()
            val get_delivery_time = lblget_time_delivery.text.toString()
            val get_delivery_date = lblget_date_delivery.text.toString()

            val get_date_order = lbl_date_order.text.toString()
            val get_laundry_service = lbl_ro_laundry_service.text.toString()
            val get_list_item_selected = lbl_get_list_item4.text.toString()
            val get_payment_method = lbl_get_payment_method.text.toString()
            val get_grand_payment_amt = lbl_payment_amy_ro.text.toString()

            val get_selected_laundry_shop = lbl_getshopname4.text.toString()
            val get_laundry_shop_address = lbl_getshopaddress4.text.toString()

            myDoc.add(Paragraph("    "+ get_selected_laundry_shop + " Laundromant "))
            myDoc.add(Paragraph("   =========================== "))
            myDoc.add(Paragraph("   Address: " + get_laundry_shop_address))
            myDoc.add(Paragraph("\n\n   LAUNDRY SERVICE SELECTION "))
            myDoc.add(Paragraph("   -------------------------------------------------------------------"))
            myDoc.add(Paragraph("   Type of Laundry Service: " + "    " + get_laundry_service))
            myDoc.add(Paragraph("   List of Item: \n   "+ get_list_item_selected))
            myDoc.add(Paragraph("\n\n   PICKUP LOCATION AND DATE/TIME "))
            myDoc.add(Paragraph("   -------------------------------------------------------------------\n"))
            myDoc.add(Paragraph("   Pickup Address: "+ "            " + get_pickup_address))
            myDoc.add(Paragraph("   Pickup Time Slot: "+ "          " + get_pickup_time))
            myDoc.add(Paragraph("   Pickup Date: "+ "                 " + get_pickup_date))
            myDoc.add(Paragraph("\n\n   DELIVERY LOCATION AND DATE/TIME "))
            myDoc.add(Paragraph("   -------------------------------------------------------------------"))
            myDoc.add(Paragraph("   Delivery Address: "  + "            " + get_delivery_address))
            myDoc.add(Paragraph("   Delivery Time Slot: "  + "          " + get_delivery_time))
            myDoc.add(Paragraph("   Delivery Date: "  + "                  " + get_delivery_date))
            myDoc.add(Paragraph("   Date Order: " + "                     " + get_date_order))
            myDoc.add(Paragraph("   Payment Method: " + "           " + get_payment_method))
            myDoc.add(Paragraph("   -------------------------------------------------------------------"))
            myDoc.add(Paragraph("   Payment Amount (RM):       RM " + get_grand_payment_amt))
            myDoc.add(Paragraph("   -------------------------------------------------------------------"))
            myDoc.close()

            Toast.makeText(this, "Order receipt has been downloaded and saved your storage", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    save_Pdf_file()
                }
                else{
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun display_order(){
        val i = intent
        val get_p_address = i.getStringExtra("P4PICKUP_ADDRESS")
        lblget_address_pickup.setText(get_p_address)

        val get_d_address = i.getStringExtra("P4DELIVERY_ADDRESS")
        lblget_address_delivery.setText(get_d_address)

        val get_p_time = i.getStringExtra("PICKUP_TIME")
        val get_d_time = i.getStringExtra("DELIVERY_TIME")
        val get_p_date = i.getStringExtra("PICKUP_DATE")
        val get_d_date = i.getStringExtra("DELIVERY_DATE")

        lblget_time_pickup.setText(get_p_time)
        lblget_time_delivery.setText(get_d_time)
        lblget_date_pickup.setText(get_p_date)
        lblget_date_delivery.setText(get_d_date)

        val get_laundry_service = i.getStringExtra("P4LAUNDRY_TYPE")
        val get_payment_amt = i.getStringExtra("P4PAYMENT_AMT")
        val get_list_item_selection_final = i.getStringExtra("P4LISTITEM")
        val get_payment_method = i.getStringExtra("PAYMENTMETHOD")

        lbl_ro_laundry_service.setText(get_laundry_service)
        lbl_get_list_item4.setText(get_list_item_selection_final)
        lbl_payment_amy_ro.setText(get_payment_amt)
        lbl_get_payment_method.setText(get_payment_method)

        val get_shopname_4 = i.getStringExtra("FShop4")
        val get_shopaddress_4 = i.getStringExtra("FAddress4")

        lbl_getshopname4.setText(get_shopname_4)
        lbl_getshopaddress4.setText(get_shopaddress_4)

    }
}
