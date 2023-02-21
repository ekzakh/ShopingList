package com.terrinc.shopinglist.presentation.shoplist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.terrinc.shopinglist.R
import com.terrinc.shopinglist.domain.ShopItem
import javax.inject.Inject

class ShopListAdapter @Inject constructor() : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    private var count = 0

    var shopItemClickListener: ((ShopItem) -> Unit)? = null
    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder count: ${++count} viewType: $viewType")
        val layoutId = if (viewType == ENABLED_TYPE) R.layout.shop_item_enabled else R.layout.shop_item_disabled
        val view = LayoutInflater.from(parent.context).inflate(
            layoutId,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        viewHolder.bind(shopItem)
        viewHolder.view.setOnLongClickListener {
            shopItemLongClickListener?.invoke(shopItem)
            true
        }
        viewHolder.view.setOnClickListener {
            shopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) ENABLED_TYPE else DISABLED_TYPE
    }

    companion object {
        const val ENABLED_TYPE = 10
        const val DISABLED_TYPE = 11
        const val MAX_POOL_SIZE = 15
    }
}
