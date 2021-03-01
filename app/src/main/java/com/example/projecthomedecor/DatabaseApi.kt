package com.example.projecthomedecor

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONObject

public class DatabaseApi {
    companion object {
        fun loginCheck(username: String, password: String): String {
            var status = ""

            val httpAsync = "http://pc.mosmai.me:13000/userlogin"
                .httpPost().body(
                    "{ \"username\" : \"${username}\"," +
                            "\"password\" : \"${password}\" }"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            if (status == "success") {

                                Global.username =
                                    JSONObject(returnData["data"].toString())["username"].toString();
                                Global.userEmail =
                                    JSONObject(returnData["data"].toString())["user_email"].toString();
                                Global.userType =
                                    JSONObject(returnData["data"].toString())["user_type"].toString();
                                Global.accessToken = returnData["access_token"].toString()

                            } else {

                            }
                            Global.loginStatus = status;

//
                        }
                    }
                }

            httpAsync.join()

            return status

        }


        fun getCurrentPassword(): String {
            var status = ""
            var password = ""

            val httpAsync = "http://pc.mosmai.me:13000/getuserdata"
                .httpPost().body(
                    "{ \"tokenid\" : \"${Global.accessToken}\"}"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            if (status == "success") {

                                password =
                                    JSONObject(returnData["data"].toString())["password"].toString();


                            } else {

                            }


//
                        }
                    }
                }

            httpAsync.join()

            return password

        }

        fun Test(username: String) {

            val httpAsync = "http://pc.mosmai.me:10080/product.php?data=$username"
                .httpPost().body(
                    "{ \"data\" : \"${username}\" }"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);


//
                        }
                    }
                }

            httpAsync.join()

        }

        fun getAllProduct(searchText: String = ""): ArrayList<ProductRecommend> {
            var status = ""
            var DataProduct = arrayListOf<ProductRecommend>()

            val httpAsync = "http://pc.mosmai.me:13000/allproduct?searchtext=${searchText}"
                .httpGet().header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);
                            println(returnData["data"])
                            var ArrayData = JSONArray(returnData["data"].toString())
                            for (i in 0 until ArrayData.length()) {
                                val item = ArrayData.getJSONObject(i)
                                DataProduct.add(
                                    ProductRecommend(
                                        item["product_id"].toString().toInt(),
                                        item["product_name"].toString(),
                                        item["product_price"].toString().toFloat(),

                                        item["balancestock"].toString().toInt()
                                    )
                                )

                                // Your code here
                            }
//                            var arrayProduct = JSONArray(returnData["data"].toString());

//                            returnData["data"].for
//                            var DataProduct2 = JSONObject(returnData["data"].toString())


                            status = returnData["status"].toString();
                            if (status == "success") {


//                                DataProduct.add(ProductRecommend(1,"โตีะ",500F,"gdg.jpg",1,"ห้องทำงาน"))


                            } else {

                            }


//
                        }
                    }
                }

            httpAsync.join()

            return DataProduct

        }

        fun getProductWithType(producttype_id: Int): ArrayList<ProductRecommend> {
            var status = ""
            var DataProduct = arrayListOf<ProductRecommend>()

            val httpAsync = "http://pc.mosmai.me:13000/getproducttype"
                .httpPost().header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                ).body(
                    "{ \"producttype_id\" : ${producttype_id}}"
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);
                            println(returnData["data"])
                            var ArrayData = JSONArray(returnData["data"].toString())
                            for (i in 0 until ArrayData.length()) {
                                val item = ArrayData.getJSONObject(i)
                                DataProduct.add(
                                    ProductRecommend(
                                        item["product_id"].toString().toInt(),
                                        item["product_name"].toString(),
                                        item["product_price"].toString().toFloat(),

                                        item["balancestock"].toString().toInt()
                                    )
                                )

                                // Your code here
                            }
//                            var arrayProduct = JSONArray(returnData["data"].toString());

//                            returnData["data"].for
//                            var DataProduct2 = JSONObject(returnData["data"].toString())


                            status = returnData["status"].toString();
                            if (status == "success") {


//                                DataProduct.add(ProductRecommend(1,"โตีะ",500F,"gdg.jpg",1,"ห้องทำงาน"))


                            } else {

                            }


//
                        }
                    }
                }

            httpAsync.join()

            return DataProduct

        }

        fun getUserData(): String {
            var status = ""
            var userData = ""

            val httpAsync = "http://pc.mosmai.me:13000/getuserdata"
                .httpPost().body(
                    "{ \"tokenid\" : \"${Global.accessToken}\"}"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            if (status == "success") {

                                userData = JSONObject(returnData["data"].toString()).toString();


                            } else {

                            }


//
                        }
                    }
                }

            httpAsync.join()

            return userData;

        }

        fun getUserAddress(): String {
            var userData = getUserData()
            var address = JSONObject(userData)["user_address"].toString()
            return address
        }

        fun getProductWithProductId(product_id: Int): ProductRecommend? {
            var status = ""
            var DataProduct: ProductRecommend? = null;

            val httpAsync = "http://pc.mosmai.me:13000/getproducttype"
                .httpPost().header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                ).body(
                    "{ \"product_id\" : ${product_id}}"
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);
                            println(returnData["data"])
                            var ArrayData = JSONArray(returnData["data"].toString())

                            val item = ArrayData.getJSONObject(0)
                            DataProduct = ProductRecommend(
                                item["product_id"].toString().toInt(),
                                item["product_name"].toString(),
                                item["product_price"].toString().toFloat(),
                                item["balancestock"].toString().toInt()
                            )


//                            var arrayProduct = JSONArray(returnData["data"].toString());

//                            returnData["data"].for
//                            var DataProduct2 = JSONObject(returnData["data"].toString())


                            status = returnData["status"].toString();
                            if (status == "success") {


//                                DataProduct.add(ProductRecommend(1,"โตีะ",500F,"gdg.jpg",1,"ห้องทำงาน"))


                            } else {

                            }


//
                        }
                    }
                }

            httpAsync.join()

            return DataProduct

        }

        fun changePassword(newPassword: String): String {
            var status = ""

            val httpAsync = "http://pc.mosmai.me:13000/changepassword"
                .httpPost().body(
                    "{ \"tokenid\" : \"${Global.accessToken}\"," +
                            "\"newpassword\" : \"${newPassword}\" }"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            if (status == "success") {


                            } else {

                            }


//
                        }
                    }
                }

            httpAsync.join()

            return status

        }

        fun editUser(
            fullname: String = "",
            email: String = "",
            gender: String = "",
            address: String = "",
            phone: String = "",
            birthday: String = ""
        ): String {
            var status = ""

            val httpAsync = "http://pc.mosmai.me:13000/edituser"
                .httpPost().body(
                    "{ \"tokenid\" : \"${Global.accessToken}\"," +
                            "\"user_address\" : \"${address}\"," +
                            "\"user_gender\" : \"${gender}\"," +
                            "\"user_email\" : \"${email}\"," +
                            "\"user_phone\" : \"${phone}\"," +
                            "\"user_birthday\" : \"${birthday}\"," +
                            "\"user_name\" : \"${fullname}\" }"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            if (status == "success") {


                            } else {

                            }
                        }
                    }
                }

            httpAsync.join()

            return status

        }

        fun getProductType(
            productId: String = ""
        ): String {
            var status = ""
            var typeString = ""
            var puredata = ""

            val httpAsync = "http://pc.mosmai.me:13000/getproducttype"
                .httpPost().body(
                    "{\"product_id\" : ${productId}}"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            puredata = data

                            var productType = JSONArray(returnData["data"].toString());
                            for (i in 0 until productType.length()) {
                                val item = productType.getJSONObject(i)
                                typeString += "${item["producttype_name"]} "

                                // Your code here
                            }

                            if (status == "success") {


                            } else {

                            }
                        }
                    }
                }

            httpAsync.join()

            return typeString

        }

        fun getAllOrder(
            OrderStatus: String = ""
        ): ArrayList<Orders> {
            Global.allOrder.clear()
            var status = ""
            var typeString = ""
            var order = null
            var puredata = ""
            var arrayListOrder = arrayListOf<Orders>()

            val httpAsync = "http://pc.mosmai.me:13000/allorder?order_status=unpaid"
                .httpPost().body(
                    "{}"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            puredata = data

                            var order = JSONArray(returnData["data"].toString());
                            for (i in 0 until order.length()) {
                                val item = order.getJSONObject(i)

                                arrayListOrder.add(
                                    Orders(
                                        item["Order_id"].toString(),
                                        item["Orders_date"].toString(),
                                        item["trackingnumber"].toString(),
                                        item["Orders_totalprice"].toString(),
                                        item["payment_id"].toString(),
                                        item["user_id"].toString(),
                                        item["Orders_status"].toString()
                                    )
                                )

                                // Your code here
                            }

                            if (status == "success") {


                            } else {

                            }
                        }
                    }
                }

            httpAsync.join()

            return arrayListOrder

        }

        fun addUser(
            username: String = "",
            password: String = "",
            fullname: String = "",
            email: String = "",
            gender: String = "",
            address: String = "",
            phone: String = "",
            birthday: String = ""
        ): String {
            var status = ""

            val httpAsync = "http://pc.mosmai.me:13000/adduser"
                .httpPost().body(
                    "{ \"password\" : \"${password}\"," +
                            "\"user_address\" : \"${address}\"," +
                            "\"username\" : \"${username}\"," +
                            "\"user_gender\" : \"${gender}\"," +
                            "\"user_email\" : \"${email}\"," +
                            "\"user_phone\" : \"${phone}\"," +
                            "\"user_birthday\" : \"${birthday}\"," +
                            "\"user_name\" : \"${fullname}\" }"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            status = returnData["status"].toString();
                            if (status == "success") {


                            } else {

                            }
                        }
                    }
                }

            httpAsync.join()

            return status

        }

        fun addProduct(
            product_name: String = "",
            product_price: Float = 0.0F,
            product_image: String = "[]",
            productType_id: String = "[]",
            balancestock: Int = 0
        ): String {
            var productid = ""

            val httpAsync = "http://pc.mosmai.me:13000/addproduct"
                .httpPost().body(
                    "{ \"tokenid\" : \"${Global.accessToken}\"," +
                            "\"product_name\" : \"${product_name}\"," +
                            "\"product_price\" : ${product_price}," +
                            "\"product_image\" : \"${product_image}\"," +
                            "\"productType_id\" : \"${productType_id}\"," +
                            "\"balancestock\" : ${balancestock}}"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            productid = returnData["insertId"].toString();

                        }
                    }
                }

            httpAsync.join()

            return productid

        }

        fun addOrder(
            totalPrice: Float
        ): String {
            var orderId = ""

            val httpAsync = "http://pc.mosmai.me:13000/addorder"
                .httpPost().body(
                    "{ \"tokenid\" : \"${Global.accessToken}\"," +
                            "\"Orders_totalprice\" : ${totalPrice}}"
                ).header(
                    mapOf(
                        "Content-Type" to "application/json"
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
//                        test1.text = ex.toString();
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                            var returnData = JSONObject(data);

                            orderId = returnData["insertId"].toString();

                        }
                    }
                }

            httpAsync.join()

            return orderId

        }
    }
}