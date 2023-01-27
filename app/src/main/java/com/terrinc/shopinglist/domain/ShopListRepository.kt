package com.terrinc.shopinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun getShopItem(shopItemId: Int): ShopItem
    fun getShopItems(): LiveData<List<ShopItem>>
}
