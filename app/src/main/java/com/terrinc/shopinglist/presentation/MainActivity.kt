package com.terrinc.shopinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.terrinc.shopinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { shopList ->
            shopListAdapter.setShopList(shopList)
        }
    }

    private fun setupRecycler() {
        shopListAdapter = ShopListAdapter()
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        with(recycler) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED_TYPE, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_TYPE, ShopListAdapter.MAX_POOL_SIZE)
        }
    }

}
