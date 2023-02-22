package com.terrinc.shopinglist.presentation.shoplist

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.terrinc.shopinglist.R
import com.terrinc.shopinglist.ShopListApp
import com.terrinc.shopinglist.data.ShopItemProvider
import com.terrinc.shopinglist.domain.ShopItem
import com.terrinc.shopinglist.presentation.ViewModelFactory
import com.terrinc.shopinglist.presentation.shopitem.ShopItemActivity
import com.terrinc.shopinglist.presentation.shopitem.ShopItemFragment
import javax.inject.Inject
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), ShopItemFragment.EditFinishedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var shopListAdapter: ShopListAdapter

    private lateinit var viewModel: ShopListViewModel
    private var shopItemContainer: FragmentContainerView? = null

    private val component by lazy {
        (application as ShopListApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shopItemContainer)
        setupRecycler()

        viewModel = ViewModelProvider(this, viewModelFactory)[ShopListViewModel::class.java]

        viewModel.shopList.observe(this) { shopList ->
            shopListAdapter.submitList(shopList)
        }
        val button = findViewById<FloatingActionButton>(R.id.addButton)
        button.setOnClickListener {
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentAddItem(this))
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.terrinc.shopinglist/shop_items"),
                null,
                null,
                null,
                null
            )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(ShopItemProvider.ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(ShopItemProvider.NAME))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow(ShopItemProvider.COUNT))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow(ShopItemProvider.ENABLED)) > 0

                val shopItem = ShopItem(id = id, name = name, count = count, enabled = enabled)
                Log.d("MainActivity", "$shopItem")
            }
            cursor?.close()
        }
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecycler() {
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        with(recycler) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED_TYPE, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_TYPE, ShopListAdapter.MAX_POOL_SIZE)
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeRecycler(recycler)
    }

    private fun setupClickListener() {
        shopListAdapter.shopItemClickListener = { shopItem ->
            Log.d("MainActivity", "shopItemClick: $shopItem")
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentEditItem(this, shopItem.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(shopItem.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.shopItemLongClickListener = { shopItem ->
            viewModel.changeEnableStateShopItem(shopItem)
        }
    }

    private fun setupSwipeRecycler(recyclerView: RecyclerView) {
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onEditFinished() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }

}
