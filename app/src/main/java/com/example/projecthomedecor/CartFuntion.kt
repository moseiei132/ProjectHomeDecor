package com.example.projecthomedecor

class CartFuntion {
    companion object{
        fun AddToCart(productId: Int, Amount: Int){
            if(Global.cart.size > 0) {
                for (i in 0 until Global.cart.size) {
                    val item = Global.cart[i]

                    if (item.product_id == productId){
                        var productDetail = DatabaseApi.getProductWithProductId(productId)
                        if (Global.cart[i].amount >= productDetail!!.balancestock as Int){
                            Global.cart[i].amount = productDetail!!.balancestock as Int
                        }else Global.cart[i].amount += Amount
                        return
                    }
                }
                Global.cart.add(Cart(productId, Amount))
            }else{
                Global.cart.add(Cart(productId, Amount))
            }
        }

        fun EditCartAmount(productId: Int, Amount: Int){
            if(Amount == 0 ) DeleteCart(productId)
            if(Global.cart.size > 0) {
                for (i in 0 until Global.cart.size) {
                    val item = Global.cart[i]

                    if (item.product_id == productId){
                        Global.cart[i].amount = Amount
                        return
                    }
                }

            }
        }

        fun DeleteCart(productId: Int){
            if(Global.cart.size > 0) {
                for (i in 0 until Global.cart.size) {
                    val item = Global.cart[i]

                    if (item.product_id == productId){
                        Global.cart.removeAt(i)
                        return
                    }
                }

            }
        }
    }
}