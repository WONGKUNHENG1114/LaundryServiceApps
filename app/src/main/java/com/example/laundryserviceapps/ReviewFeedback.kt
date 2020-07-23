package com.example.laundryserviceapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryserviceapps.Adapter.FeedbackRecyclerView
import com.example.laundryserviceapps.ClassModel.Feedback
import com.example.laundryserviceapps.DatabaseHelper.SQLiteHelper
import kotlinx.android.synthetic.main.activity_review_feedback.*
import java.text.SimpleDateFormat
import java.util.*

class ReviewFeedback : AppCompatActivity() {

    lateinit var handler: SQLiteHelper
    private var lstfeedback = ArrayList<Feedback>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_feedback)

        handler = SQLiteHelper(this)
        val i = intent
//        val get_laundry_shop = i.getStringExtra("Shop_name_feedback1")
//        lblgetShopNameFeedback.setText(get_laundry_shop)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)

        lblpostdate.text = formatedDate.toString()

        display_feedback()

        btnSendFeedback.setOnClickListener {
            validate_and_post_feedback()
            display_feedback()
        }

        imageButtonclose.setOnClickListener {
            finish()
        }
    }

    fun validate_and_post_feedback() {
//        val laundryshop = lblgetShopNameFeedback.text.toString()
//        handler.onCreateFeedback()
        val getrate = rating_service.rating.toString()
        val comment = edtcomment.text.toString()

        if (comment.equals("")) {
            Toast.makeText(this, "Please comment our services.", Toast.LENGTH_LONG).show()
        } else {
            handler.postFeedback(feedback = Feedback(0,getrate.toDouble(),comment,lblpostdate.text.toString()))
            Toast.makeText(this, "Feedback has been posted.", Toast.LENGTH_LONG).show()
            clear()
        }
    }

    fun display_feedback(){
        lstfeedback= handler.getFeedback()

        val adapter = FeedbackRecyclerView(lstfeedback)
        rvfeedback.layoutManager = LinearLayoutManager(this)
        rvfeedback.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        display_feedback()
        super.onResume()
    }

    fun clear(){
        rating_service.setRating(0.0F)
        edtcomment.setText("")
    }
}