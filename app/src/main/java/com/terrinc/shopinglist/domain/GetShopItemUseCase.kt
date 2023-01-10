package com.terrinc.shopinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getItem(shopItemId: Int): ShopItem {
        return shopListRepository.getShopItem(shopItemId)
    }
}
