package com.example.projecthomedecor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View

class Category : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.categoryframe,
                ProductTypeFragment()
            ).addToBackStack(null).commit()
        }
    }

    fun gohome(item: MenuItem) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    fun gocategory(item: MenuItem) {

    }
    fun gocart(item: MenuItem) {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
    fun goprofile(item: MenuItem) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
    fun type_bedroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(1)
            supportFragmentManager.beginTransaction().add(
                R.id.categoryframe,
                ProductTypeSegmentFragment()
            ).addToBackStack(null).commit()
    }
    fun type_kitchenroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(2)
        supportFragmentManager.beginTransaction().add(
            R.id.categoryframe,
            ProductTypeSegmentFragment()
        ).addToBackStack(null).commit()
    }
    fun type_livingroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(3)
        supportFragmentManager.beginTransaction().add(
            R.id.categoryframe,
            ProductTypeSegmentFragment()
        ).addToBackStack(null).commit()
    }
    fun type_bathroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(4)
        supportFragmentManager.beginTransaction().add(
            R.id.categoryframe,
            ProductTypeSegmentFragment()
        ).addToBackStack(null).commit()
    }
    fun type_workroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(5)
        supportFragmentManager.beginTransaction().add(
            R.id.categoryframe,
            ProductTypeSegmentFragment()
        ).addToBackStack(null).commit()
    }
    fun type_diningroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(6)
        supportFragmentManager.beginTransaction().add(
            R.id.categoryframe,
            ProductTypeSegmentFragment()
        ).addToBackStack(null).commit()
    }
    fun type_storageroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(7)
        supportFragmentManager.beginTransaction().add(
            R.id.categoryframe,
            ProductTypeSegmentFragment()
        ).addToBackStack(null).commit()
    }
    fun type_drawingroom(view: View) {
        Global.allProductWithType = DatabaseApi.getProductWithType(8)
        supportFragmentManager.beginTransaction().add(
            R.id.categoryframe,
            ProductTypeSegmentFragment()
        ).addToBackStack(null).commit()
    }

}