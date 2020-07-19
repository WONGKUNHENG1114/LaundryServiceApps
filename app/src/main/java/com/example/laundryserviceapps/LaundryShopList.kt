package com.example.laundryserviceapps

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryserviceapps.Adapter.LaundryShopRecycler
import com.example.laundryserviceapps.Adapter.OrderHolder
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_laundry_shop_list.*
import java.util.*
import kotlin.collections.ArrayList

class LaundryShopList : AppCompatActivity() {

    lateinit var handler: SQLiteHelper
    val arrayList = ArrayList<Laundry_Shop>()
    val displayList = ArrayList<Laundry_Shop>()
//    private var lstLS = ArrayList<Laundry_Shop>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laundry_shop_list)

//        handler = SQLiteHelper(this)
        arrayList.add(Laundry_Shop(0,"Kat Dobi","No 18, Kampung Poh Wah, 11200, Tanjong Bungah, Pulau Pinang","","Active"))
        arrayList.add(Laundry_Shop(0,"Orchard Dobi","No 11, Lebuh Orchard, 11200, Tanjong Bungah, Pulau Pinang","","Active"))
        arrayList.add(Laundry_Shop(0,"Clean Dobi","No 12, Taman Bukit Cheras, 56000, Cheras, Kuala Lumpur","","Active"))
        arrayList.add(Laundry_Shop(0,"Everday Dobi","No 11, Jalan Tokoh 25/28, 40400, Shah Alam, Selangor","","Active"))
        arrayList.add(Laundry_Shop(0,"24Hours Dobi","No 12, Jln Midah 8A, 56000, Cheras, Kuala Lumpur","","Active"))
        arrayList.add(Laundry_Shop(0,"CLS Dobi","No 15, Jalan Bukit Kemuning, 40400, Shah Alam, Selangor","","Active"))
        displayList.addAll(arrayList)

        val adapter = LaundryShopRecycler(displayList,this)
        recyclerlaundryshop.layoutManager = LinearLayoutManager(this)
        recyclerlaundryshop.adapter = adapter

        imgback7.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this)
            mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
            mAlertDialog.setTitle("INFO")
            mAlertDialog.setMessage("Are you sure want to cancel the order? \nOnce you cancelled the order, all the services are ordered will not be saved")
            mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                finish()
            }
            mAlertDialog.setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.setNeutralButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
            mAlertDialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuItem = menu!!.findItem(R.id.search)

        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            val edittext = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
//            edittext.hint  = "Search ... "

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()){
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())

                        arrayList.forEach {
                            if(it.laundry_shop_Address.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList.add(it)
                            }
                        }
                        recyclerlaundryshop.adapter!!.notifyDataSetChanged()
                    } else {
                        displayList.clear()
                        displayList.addAll(arrayList)
                        recyclerlaundryshop.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
//        handler = SQLiteHelper(this)
//        lstLS= handler?.alllaundryShop()
//
//        val adapter = LaundryShopRecycler(lstLS)
//        recyclerlaundryshop.layoutManager = LinearLayoutManager(this)
//        recyclerlaundryshop.adapter = adapter

}