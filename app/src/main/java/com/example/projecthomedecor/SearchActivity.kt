package com.example.projecthomedecor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.hide()

        val productSearch = intent.getStringExtra("searchText")
        edt_search.setText(productSearch)

        var dataList = arrayListOf<ProductRecommend>()
        //Toast.makeText(applicationContext, if(productSearch!=null)productSearch else "no", Toast.LENGTH_LONG).show()
        dataList= DatabaseApi.getAllProduct(if(productSearch!=null)productSearch else "")
        reccom_recycler_view.adapter = ProductRecAdapter(dataList, applicationContext)
        reccom_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        reccom_recycler_view.itemAnimator = DefaultItemAnimator()

    }

    fun btnSearch(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra("searchText",edt_search.text.toString())
        finish()
        startActivity(intent)
    }

}