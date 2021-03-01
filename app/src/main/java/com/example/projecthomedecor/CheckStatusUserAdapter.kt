package com.example.projecthomedecor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.check_status_order_layout.view.*
import kotlinx.android.synthetic.main.check_status_user_layout.view.*

class CheckStatusUserAdapter(val items: ArrayList<Orders>, val context: Context) :
    RecyclerView.Adapter<CheckStatusUserAdapter.ViewHolder>() {class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var OrderId = view.order_id
    var Totalprice = view.order_price
    var Status = view.textStatus
}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckStatusUserAdapter.ViewHolder {
        val view_ckeckOrder = LayoutInflater.from(parent.context)
            .inflate(R.layout.check_status_user_layout, parent, false)
        val myHolder = CheckStatusUserAdapter.ViewHolder(view_ckeckOrder)

        return myHolder
    }

    override fun onBindViewHolder(holder: CheckStatusUserAdapter.ViewHolder, position: Int) {
        holder.OrderId.text = "ชื่อสินค้า : " + items[position].Order_id
        holder.Totalprice.text = "ราคารวม : " + items[position].Orders_totalprice
        holder.Status.text
    }

    override fun getItemCount(): Int {
        return items.size
    }
}