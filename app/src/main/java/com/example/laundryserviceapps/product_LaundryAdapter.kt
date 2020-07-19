package com.example.laundryserviceapps

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.product_LaundryShopModelClass


class product_LaundryAdapter (private val context: Context, private val mLaundryShopList: List<product_LaundryShopModelClass>) : RecyclerView.Adapter<product_LaundryAdapter.ViewHolder>(){
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val laundryShopName = itemView.findViewById<TextView>(R.id.textViewLaundryShopName)
        val laundryShopAddress = itemView.findViewById<TextView>(R.id.textViewAddress)
        val laundryShopTelNo = itemView.findViewById<TextView>(R.id.textViewTelNo)
        val laundryShopImage = itemView.findViewById<ImageView>(R.id.imageViewLaundry)
        val btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
    }
    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): product_LaundryAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.product_laundryshop_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: product_LaundryAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val laundryShop: product_LaundryShopModelClass = this.mLaundryShopList[position]
        // Set item views based on your views and data model
        val arrAddresss=laundryShop?.shopAddress?.split("@@")
        val address="${arrAddresss?.get(0)}${arrAddresss?.get(1)}${arrAddresss?.get(2)}"
        val textViewLaundryShopName = viewHolder.laundryShopName
        val textViewlaundryShopAddress = viewHolder.laundryShopAddress
        val textViewlaundryShopTelNo = viewHolder.laundryShopTelNo
        val laundryShopImage = viewHolder.laundryShopImage
        val btnEditItem=viewHolder.btnEdit
        textViewLaundryShopName.text = laundryShop.shopName
        textViewlaundryShopAddress.text= address
        textViewlaundryShopTelNo.text= " ${laundryShop.phoneNo}"
        val bitmapImage=laundryShop.getImage()
        laundryShopImage.setImageBitmap(bitmapImage)

        btnEditItem.setOnClickListener {
           val i=Intent(context,product_LaundryEditShopItem::class.java)
            i.putExtra("shopName",laundryShop.shopName)
            context.startActivity(i)

        }

    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mLaundryShopList.size
    }


}

