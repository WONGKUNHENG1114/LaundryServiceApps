package com.example.laundryserviceapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryserviceapps.ClassModel.Feedback
import com.example.laundryserviceapps.ClassModel.Order
import com.example.laundryserviceapps.ClassModel.Promotion
import com.example.laundryserviceapps.MainActivity
import com.example.laundryserviceapps.R
import kotlinx.android.synthetic.main.list_of_promotion.view.*

class FeedbackRecyclerView(val feedback:ArrayList<Feedback>): RecyclerView.Adapter<FeedbackRecyclerView.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val lbl_comment  = itemView.findViewById(R.id.lbl_comment) as TextView
        val lbl_rating  = itemView.findViewById(R.id.lbl_rating) as TextView
        val lbl_date_post_comment  = itemView.findViewById(R.id.lbl_date_post_comment) as TextView
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v: View = LayoutInflater.from(p0.context).inflate(R.layout.list_feedback,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return feedback.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val feedback: Feedback = feedback[p1]
        p0.lbl_comment.text = feedback.comment
        p0.lbl_rating.text = feedback.feedback_rate.toString()
        p0.lbl_date_post_comment.text = feedback.post_date
//        notifyDataSetChanged()
    }
}