package com.terrinc.shopinglist.domain

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(shopId: Int): ShopItem
    fun getShopItems(): List<ShopItem>
}
