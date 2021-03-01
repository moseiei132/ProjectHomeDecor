package com.example.projecthomedecor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import kotlinx.android.synthetic.main.basket_add_product_layout.view.*
import retrofit2.Call
import retrofit2.Response


class CartAdapter(val items: List<Cart>, val context: Context) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cName = view.product_name
        var cPrice = view.product_price
        var cImage = view.productAddbasketView
        var cAmount = view.product_amount
        var cDelete = view.deleteProduct
        var AppContext = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view_cart = LayoutInflater.from(parent.context)
            .inflate(R.layout.basket_add_product_layout, parent, false)
        val myHolder = CartAdapter.ViewHolder(view_cart)

        return myHolder
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        var productDetail = DatabaseApi.getProductWithProductId(items[position].product_id)
        Glide.with(context)
            .load("http://pc.mosmai.me:10080/product/${items[position].product_id.toString()}/1.jpg")
            .into(holder.cImage)
        holder.cName.text = "ชื่อสินค้า : " + productDetail!!.productName.toString()
        holder.cPrice.text = "ราคาสินค้า " + productDetail!!.productPrice.toString() + "บาท"
        holder.cAmount.setNumber(items[position].amount.toString())

        val Cart = items[position]

        holder.cDelete.setOnClickListener {


            val myBuilder = AlertDialog.Builder(holder.AppContext)
            myBuilder.apply {
                setTitle("Warning Message")
                setMessage("Do you want to delete the product?")
                setNegativeButton("Yes"){ dialog,which ->
                    CartFuntion.DeleteCart(Cart.product_id)
                    Global.restartCart = "yes"

                }
                setPositiveButton("No"){dialog,which->dialog.cancel()}
                show()
            }

        }




        holder.cAmount.setOnValueChangeListener(ElegantNumberButton.OnValueChangeListener { view, oldValue, newValue ->
            if(newValue > productDetail!!.balancestock as Int){
                holder.cAmount.setNumber(oldValue.toString())
                CartFuntion.EditCartAmount(items[position].product_id, oldValue)
            }else if(newValue < productDetail!!.balancestock as Int){
//                holder.cAmount.setNumber(newValue.toString())
                CartFuntion.EditCartAmount(items[position].product_id, newValue.toString().toInt())
            }
            if (newValue == 0) {
//                holder.cAmount.visibility = View.INVISIBLE
//                holder.itemView.visibility = View.INVISIBLE
                val myBuilder = AlertDialog.Builder(holder.AppContext)
                myBuilder.apply {
                    setTitle("Warning Message")
                    setMessage("Do you want to delete the product?")
                    setNegativeButton("Yes"){ dialog,which ->
                        CartFuntion.DeleteCart(Cart.product_id)
                        Global.restartCart = "yes"

                    }
                    setPositiveButton("No"){dialog,which->
                        holder.cAmount.setNumber("1")
                        CartFuntion.EditCartAmount(items[position].product_id, 1)
                        dialog.cancel()}
                    show()
                }

            }

        })

    }

    override fun getItemCount(): Int {
        return items.size
    }
}