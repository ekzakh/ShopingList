package com.terrinc.shopinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.terrinc.shopinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { shopList ->
            adapter.setShopList(shopList)
        }
    }

    private fun setupRecycler() {
        adapter = ShopListAdapter()
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
    }

}
