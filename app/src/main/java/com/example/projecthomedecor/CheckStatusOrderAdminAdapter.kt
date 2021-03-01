package com.example.projecthomedecor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.basket_add_product_layout.view.*
import kotlinx.android.synthetic.main.check_status_order_layout.view.*

class CheckStatusOrderAdminAdapter (val items: ArrayList<Orders>, val context: Context) :
    RecyclerView.Adapter<CheckStatusOrderAdminAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var OrderId = view.order_id
        var Totalprice = view.order_price

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckStatusOrderAdminAdapter.ViewHolder {
        val view_ckeckOrederAdmin = LayoutInflater.from(parent.context)
            .inflate(R.layout.check_status_order_layout, parent, false)
        val myHolder = CheckStatusOrderAdminAdapter.ViewHolder(view_ckeckOrederAdmin)

        view_ckeckOrederAdmin.setOnClickListener() {
            val pos = myHolder.adapterPosition
            val context: Context = parent.context
            val orderCheck = items[pos]
            val intent = Intent(context, NotificationShippingActivity::class.java)
            intent.putExtra("orderID",orderCheck.Order_id)
            context.startActivity(intent)
        }
        return myHolder
    }

    override fun onBindViewHolder(holder: CheckStatusOrderAdminAdapter.ViewHolder, position: Int) {
        holder.OrderId.text = "ชื่อสินค้า : " + items[position].Order_id
        holder.Totalprice.text = "ราคารวม : " + items[position].Orders_totalprice
    }

    override fun getItemCount(): Int {
        return items.size
    }
}