package com.example.projecthomedecor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.mainframe,
                SearchFragment()
            ).commit()
        }
    }
    override fun onResume() {
        super.onResume()

    }


    fun gohome(item: MenuItem) {

    }

    fun gocategory(item: MenuItem) {
        val intent = Intent(this, Category::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
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

    fun searchproduct(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra("searchText",edt_search.text.toString())
        startActivity(intent)
    }

}