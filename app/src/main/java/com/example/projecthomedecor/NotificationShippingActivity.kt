package com.example.projecthomedecor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notification_shipping.*
import kotlinx.android.synthetic.main.fragment_search.*

class NotificationShippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_shipping)

        val orderID = intent.getStringExtra("orderID")
        orderid.setText(orderID)
    }
}