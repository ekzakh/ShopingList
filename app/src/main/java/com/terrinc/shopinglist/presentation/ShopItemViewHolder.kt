package com.terrinc.shopinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.terrinc.shopinglist.R
import com.terrinc.shopinglist.domain.ShopItem

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val tvName: TextView = view.findViewById(R.id.name)
    private val tvCount: TextView = view.findViewById(R.id.count)
    fun bind(shopItem: ShopItem) {
        tvName.text = shopItem.name
        tvCount.text = shopItem.count.toString()
    }
}
