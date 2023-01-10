package com.terrinc.shopinglist.data

import com.terrinc.shopinglist.domain.ShopItem
import com.terrinc.shopinglist.domain.ShopListRepository

object ShopListRepositoryImp : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw java.lang.RuntimeException("Element with $shopItemId not found")
    }

    override fun getShopItems(): List<ShopItem> {
        return shopList.toList()
    }
}
