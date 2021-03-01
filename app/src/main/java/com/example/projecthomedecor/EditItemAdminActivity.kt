package com.example.projecthomedecor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_edit_item_admin.*

class EditItemAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item_admin)

        val productN = intent.getStringExtra("productName").toString()
        val productP = intent.getStringExtra("productPrice").toString()
        val productA = intent.getStringExtra("balancestock").toString()

        edit_name_product.setText(productN)
        edt_price_product.setText(productP)
        edt_warehouse_product.setText(productA)
    }

    fun clickUpdateProduct(view: View) {

    }
    fun clickDeleteProduct(view: View) {

    }
}