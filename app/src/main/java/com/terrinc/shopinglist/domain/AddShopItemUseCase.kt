package com.terrinc.shopinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun addItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}
