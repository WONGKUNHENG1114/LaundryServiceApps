package com.example.laundryserviceapps

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.laundryserviceapps.DatabaseHandler.product_databaseHandler
import kotlinx.android.synthetic.main.product_view_shop_item.*

class product_viewShopItem: AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var adapter:product_LaundryAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_view_shop_item)
        retrieveData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuSearch=menu?.findItem(R.id.search)
        val searchView=menuSearch?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(this)
//        return super.onCreateOptionsMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun retrieveData()
    {

        val pref: SharedPreferences = this.getSharedPreferences("retailer_user_details", MODE_PRIVATE)
        val RetailerUsername= pref.getString("username", null)
        val db=
            product_databaseHandler(
                this
            )
       val  registeredRetailer=db.viewShopLaundry(RetailerUsername.toString())
        adapter=product_LaundryAdapter(this,registeredRetailer)

        rvLaundryShops.adapter=adapter
        rvLaundryShops.layoutManager= LinearLayoutManager(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

//    override fun onButtonClick(position: Int) {
//        val builder = AlertDialog.Builder(this@product_viewShopItem)
//        with(builder)
//        {
//            setTitle("Edit Profile Confirmation")
//            setMessage("Confirm to edit product profile?")
//            setPositiveButton("Confirm") { dialog, which ->
//                //intent and editPage
//                val i = Intent(context, product_LaundryEditShopItem::class.java)
//                context.startActivity(i)
//            }
//
//            setNeutralButton("Cancel") { dialog, which ->
//                //stay
//                dialog.dismiss()
//            }
//            show()
//
//        }
//    }
}