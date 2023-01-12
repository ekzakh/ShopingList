package com.terrinc.shopinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.terrinc.shopinglist.R
import com.terrinc.shopinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var shopList = listOf<ShopItem>()
    private var count = 0

    var shopItemClickListener: ((ShopItem) -> Unit)? = null
    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null

    fun setShopList(newShopList: List<ShopItem>) {
        shopList = newShopList
        notifyDataSetChanged()
    }

    fun getShopItem(position: Int): ShopItem {
        if (position >= shopList.size) throw IllegalArgumentException("Element with position $position not found")
        return shopList[position]
    }

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

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        holder.bind(shopList[position])
        holder.view.setOnLongClickListener {
            shopItemLongClickListener?.invoke(shopList[position])
            true
        }
        holder.view.setOnClickListener{
            shopItemClickListener?.invoke(shopList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enabled) ENABLED_TYPE else DISABLED_TYPE
    }

    override fun getItemCount(): Int = shopList.size

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.findViewById<TextView>(R.id.name)
        private val tvCount = view.findViewById<TextView>(R.id.count)
        fun bind(shopItem: ShopItem) {
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
        }
    }

    companion object {
        const val ENABLED_TYPE = 10
        const val DISABLED_TYPE = 11
        const val MAX_POOL_SIZE = 15
    }
}
