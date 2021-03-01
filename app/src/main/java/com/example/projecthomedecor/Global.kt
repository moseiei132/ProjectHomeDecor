package com.example.projecthomedecor

public class Global {
    companion object {
        var loginStatus: String? = null
        var userEmail: String? = null
        var username:String? = null
        var accessToken: String? = null
        var userType: String? = null
        var allProductWithType: ArrayList<ProductRecommend>? = null
        var cart = arrayListOf<Cart>()
        var restartCart = "no"
        var totalPrice: Float = 0F
        var allOrder = arrayListOf<Orders>()
    }
}