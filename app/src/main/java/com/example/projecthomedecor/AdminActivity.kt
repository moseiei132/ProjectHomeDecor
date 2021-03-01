package com.example.projecthomedecor

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import android.opengl.ETC1.encodeImage
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import kotlinx.android.synthetic.main.fragment_add_items_admin.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.io.File
import java.io.InputStream


class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.adminframe,
                AdminMenuFragment()
            ).addToBackStack(null).commit()
        }
    }

    var imgnum: String? = null
    var img1Uri: Uri? = null
    var img2Uri: Uri? = null
    var img3Uri: Uri? = null

    fun goaddproductstock(item: MenuItem) {
        supportFragmentManager.beginTransaction().add(
            R.id.adminframe,
            AddItemsAdminFragment()
        ).commit()
    }

    fun addimage1(view: View) {
        imgnum = "pic1"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery();
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

    fun addimage2(view: View) {
        imgnum = "pic2"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery();
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

    fun addimage3(view: View) {
        imgnum = "pic3"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery();
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            if (imgnum == "pic1") {
                imageView.setImageURI(data?.data)
                img1Uri = data?.data
            }
            if (imgnum == "pic2") {
                image2.setImageURI(data?.data)
                img2Uri = data?.data
            }
            if (imgnum == "pic3") {
                image3.setImageURI(data?.data)
                img3Uri = data?.data
            }

            var mos = data?.data
            var a = mos?.path as String

            DatabaseApi
        }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun btn_delete_image1(view: View) {
        imageView.setImageResource(0)
        img1Uri = null
    }

    fun btn_delete_image2(view: View) {
        image2.setImageResource(0)
        img2Uri = null
    }

    fun btn_delete_image3(view: View) {
        image3.setImageResource(0)
        img3Uri = null
    }

    fun btn_save_add_product(view: View) {
        if (img1Uri == null) {
            //Toast.makeText(applicationContext, "มึงต้องใส่รูปแรกนะ", Toast.LENGTH_LONG).show()
            return
        }
        var typeArray = arrayListOf<Int>()
        var type_product_add: String = ""

        if (cb_bedroom.isChecked) {
            typeArray.add(1)
            type_product_add += " " + cb_bedroom.text;
        }
        if (cb_workroom.isChecked) {
            typeArray.add(2)
            type_product_add += " " + cb_workroom.text;
        }
        if (cb_chickenroom.isChecked) {
            typeArray.add(3)
            type_product_add += " " + cb_chickenroom.text;
        }
        if (cb_storeroom.isChecked) {
            typeArray.add(4)
            type_product_add += " " + cb_storeroom.text;
        }
        if (cb_livingroom.isChecked) {
            typeArray.add(5)
            type_product_add += " " + cb_livingroom.text;
        }
        if (cb_bathroom.isChecked) {
            typeArray.add(6)
            type_product_add += " " + cb_bathroom.text;
        }
        if (cb_drawingroom.isChecked) {
            typeArray.add(7)
            type_product_add += " " + cb_drawingroom.text;
        }
        if (cb_foodroom.isChecked) {
            typeArray.add(8)
            type_product_add += " " + cb_foodroom.text;
        }
        var type_product_add_new =
            if (type_product_add.isNotEmpty()) type_product_add else "Please select type product."
        var name_product = add_nameitem.text.toString()
        var price_product = add_priceitem.text.toString().toInt()
        var balance_stock = add_warehouseitem.text.toString().toInt()

        var imgLink = arrayListOf<Int>()
        if (img1Uri != null) {
            imgLink.add(1)
        }
        if (img2Uri != null) {
            imgLink.add(2)
        }
        if (img3Uri != null) {
            imgLink.add(3)
        }
        var productid = DatabaseApi.addProduct(
            name_product,
            price_product.toFloat(),
            imgLink.toString(),
            typeArray.toString(),
            balance_stock
        )
        if (img1Uri != null) {
            val imageStream: InputStream? = contentResolver.openInputStream(img1Uri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            val encodedImage = encodeImage(selectedImage) as String
            Fuel.post("http://pc.mosmai.me:10080/product.php?tokenid=${Global.accessToken.toString()}&product_id=${productid}&img_num=1")
                .header(Headers.CONTENT_TYPE, "application/json")
                .body("${encodedImage}")
                .also { println(it) }
                .response { result ->
                    Toast.makeText(
                        applicationContext,
                        result.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        if (img2Uri != null) {
            val imageStream: InputStream? = contentResolver.openInputStream(img2Uri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            val encodedImage = encodeImage(selectedImage) as String
            Fuel.post("http://pc.mosmai.me:10080/product.php?tokenid=${Global.accessToken.toString()}&product_id=${productid}&img_num=2")
                .header(Headers.CONTENT_TYPE, "application/json")
                .body("${encodedImage}")
                .also { println(it) }
                .response { result ->
                    Toast.makeText(
                        applicationContext,
                        result.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        if (img3Uri != null) {
            val imageStream: InputStream? = contentResolver.openInputStream(img3Uri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            val encodedImage = encodeImage(selectedImage) as String
            Fuel.post("http://pc.mosmai.me:10080/product.php?tokenid=${Global.accessToken.toString()}&product_id=${productid}&img_num=3")
                .header(Headers.CONTENT_TYPE, "application/json")
                .body("${encodedImage}")
                .also { println(it) }
                .response { result ->
                    Toast.makeText(
                        applicationContext,
                        result.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        supportFragmentManager.popBackStack()
    }

    fun goeditproducestock(item: MenuItem) {
        supportFragmentManager.beginTransaction().add(
            R.id.adminframe,
            SearchEditItemAdminFragment()
        ).addToBackStack(null).commit()
    }



    fun gocheckorder(item: MenuItem) {
        supportFragmentManager.beginTransaction().add(
            R.id.adminframe,
            CheckStatusOrderAdminFragment()
        ).addToBackStack(null).commit()
    }

}