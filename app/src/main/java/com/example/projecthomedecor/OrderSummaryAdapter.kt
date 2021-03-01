package com.example.projecthomedecor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.order_summary_layout.view.*
import kotlinx.android.synthetic.main.reccom_item_layout.view.*

class OrderSummaryAdapter (val items : List<Cart>, val context: Context): RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var oName = view.order_name
        var oPrice = view.order_price
        var oImage = view.imageOrder
        var oAmount = view.order_amount
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int ): OrderSummaryAdapter.ViewHolder {
        val view_order = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_summary_layout, parent, false)
        val myHolder = OrderSummaryAdapter.ViewHolder(view_order)
        return myHolder
    }

    override fun onBindViewHolder(holder: OrderSummaryAdapter.ViewHolder, position: Int) {
        var productDetail = DatabaseApi.getProductWithProductId(items[position].product_id)
        Glide.with(context).load("http://pc.mosmai.me:10080/product/${items[position].product_id.toString()}/1.jpg").into(holder.oImage)
        holder.oName.text = "ชื่อสินค้า : " + productDetail!!.productName.toString()
        holder.oPrice.text = "ราคาสินค้า : " + productDetail!!.productPrice.toString() + " บาท"
        holder.oAmount.text = "จำนวนสินค้า : " + items[position].amount.toString() + " ชิ้น"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}