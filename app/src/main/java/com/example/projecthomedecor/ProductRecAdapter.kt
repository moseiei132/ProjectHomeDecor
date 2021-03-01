package com.example.projecthomedecor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.reccom_item_layout.view.*

class ProductRecAdapter (val items : List<ProductRecommend>, val context: Context):RecyclerView.Adapter<ProductRecAdapter.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var pvName = view.productText
        var pvPrice = view.priceText
        var pvImage = view.imageProduct
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_productrec = LayoutInflater.from(parent.context).inflate(R.layout.reccom_item_layout,parent,false)
        val myHolder = ViewHolder(view_productrec)

        view_productrec.setOnClickListener() {
            val pos = myHolder.adapterPosition
            val context: Context = parent.context
            val productRecommend = items[pos]
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("productName",productRecommend.productName)
            intent.putExtra("productId",productRecommend.product_id.toString())
            intent.putExtra("productPrice",productRecommend.productPrice.toString())
            //intent.putExtra("productImg",productRecommend.productImg)
//            intent.putExtra("productType_id",productRecommend.productType_id)
//            intent.putExtra("productType_name",productRecommend.productType_name)
            intent.putExtra("balancestock",productRecommend.balancestock.toString())
            context.startActivity(intent)
        }
        return myHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load("http://pc.mosmai.me:10080/product/${items[position].product_id.toString()}/1.jpg").into(holder.pvImage)
        holder.pvName.text = "ชื่อสินค้า : "+ items[position].productName
        holder.pvPrice.text = "ราคาสินค้า " + items[position].productPrice + "บาท"
    }
    override fun getItemCount(): Int {
        return items.size
    }

}