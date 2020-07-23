package com.example.laundryserviceapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.Adapter.LaundryShopSelectionRecyclerView
import com.example.laundryserviceapps.ClassModel.Laundry_Shop
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_laundry_shop_list.*
import kotlinx.android.synthetic.main.activity_laundry_shop_selection_list.*
import java.util.*
import kotlin.collections.ArrayList

class LaundryShopSelectionList : AppCompatActivity() {


    lateinit var handler: SQLiteHelper

    var lstLS = ArrayList<Laundry_Shop>()
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var rvlaundryshop_selection: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laundry_shop_selection_list)

        var intent = intent
        val get_shop1 = intent.getStringExtra("promo_name1")
        val get_address1 = intent.getStringExtra("discount1")

        lblgetpromo_name4.setText(get_shop1)
        lblgetpromo_discount4.setText(get_address1)

        handler = SQLiteHelper(this)
        create_laundry_shop()

        imgback14.setOnClickListener {
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

//    fun create_laundry_shop(){
//        try{
//            val dbhandler = SQLiteHelper(this)
////            dbhandler.onCreateLaundryShop()
//            val lstLS = dbhandler.getLaundryShop()
//            val adapter= LaundryShopSelectionRecyclerView(this, lstLS)
//            rvlaundryshop_selection.adapter = adapter
//            rvlaundryshop_selection.layoutManager = LinearLayoutManager(this)
//
//        } catch (e: Exception) {
//            Log.i("error", e.toString())
//        }
//    }

    fun create_laundry_shop(){
        try{
           // val dbhandler = SQLiteHelper(this)
            lstLS = handler.getLaundryShop()
            adapter= LaundryShopSelectionRecyclerView(this, lstLS)
            rvlaundryshop_selection = findViewById(R.id.rvlaundryshop_selection)
            rvlaundryshop_selection.adapter = adapter
            rvlaundryshop_selection.layoutManager = LinearLayoutManager(this)

        } catch (e: Exception) {
            Log.i("error", e.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuItem = menu!!.findItem(R.id.search)

        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            val edittext = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    var filteredLaundryShop = ArrayList<Laundry_Shop>()

                    if(newText!!.isNotEmpty()){
                        for(i in 0..lstLS.size - 1){
                            if(lstLS.get(i).laundry_shop_Address!!.toLowerCase(Locale.getDefault()).contains(newText))
                                filteredLaundryShop.add(lstLS[i])
                        }
                        adapter = LaundryShopSelectionRecyclerView(this@LaundryShopSelectionList,filteredLaundryShop)
                        rvlaundryshop_selection.adapter = adapter
                    } else {
                        adapter = LaundryShopSelectionRecyclerView(this@LaundryShopSelectionList,lstLS)
                        rvlaundryshop_selection.adapter = adapter
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
}