package com.example.laundryserviceapps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.Feedback
import com.example.laundryserviceapps.ClassModel.product_LaundryShopModelClass

class product_FeedbackAdapter( private val context: Context,
private val mLaundryShopFeedback: ArrayList<Feedback>
) : RecyclerView.Adapter<product_FeedbackAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val feedbackComment = itemView.findViewById<TextView>(R.id.lblComment)
        val LaundryRating = itemView.findViewById<RatingBar>(R.id.lblRating)
        val feeedbackDate = itemView.findViewById<TextView>(R.id.lblCommentDate)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): product_FeedbackAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.product_feedback_list, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val laundryShopFeedback: Feedback = this.mLaundryShopFeedback[position]
        // Set item views based on your views and data model
        val textViewComment = viewHolder.feedbackComment
        val textViewlaundryRating = viewHolder.LaundryRating
        val textViewlaundryDate = viewHolder.feeedbackDate
        textViewComment.text=laundryShopFeedback.comment
        val rateValue=laundryShopFeedback.feedback_rate
        textViewlaundryRating.rating=rateValue.toFloat()
        textViewlaundryDate.text=laundryShopFeedback.post_date
    }

    override fun getItemCount(): Int {
        return mLaundryShopFeedback.size
    }

}