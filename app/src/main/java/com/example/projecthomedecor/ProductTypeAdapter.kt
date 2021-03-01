package com.example.projecthomedecor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.reccom_item_layout.view.*

class ProductTypeAdapter(val items : List<ProductRecommend>, val context: Context): RecyclerView.Adapter<ProductTypeAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var ptName = view.productText
        var ptPrice = view.priceText
        var ptImage = view.imageProduct
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTypeAdapter.ViewHolder {
        val view_producttype = LayoutInflater.from(parent.context).inflate(R.layout.reccom_item_layout,parent,false)
        val myHolder = ProductTypeAdapter.ViewHolder(view_producttype)

        view_producttype.setOnClickListener() {
            val pos = myHolder.adapterPosition
            val context: Context = parent.context
            val productType = items[pos]
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("productName",productType.productName)
            intent.putExtra("productId",productType.product_id.toString())
            intent.putExtra("productPrice",productType.productPrice.toString())
            //intent.putExtra("productImg",productRecommend.productImg)
//            intent.putExtra("productType_id",productRecommend.productType_id)
//            intent.putExtra("productType_name",productRecommend.productType_name)
            intent.putExtra("balancestock",productType.balancestock.toString())
            context.startActivity(intent)
        }
        return myHolder
    }

    override fun onBindViewHolder(holder: ProductTypeAdapter.ViewHolder, position: Int) {
        Glide.with(context).load("http://pc.mosmai.me:10080/product/${items[position].product_id.toString()}/1.jpg").into(holder.ptImage)
        holder.ptName.text = "ชื่อสินค้า : "+ items[position].productName
        holder.ptPrice.text = "ราคาสินค้า " + items[position].productPrice + "บาท"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}