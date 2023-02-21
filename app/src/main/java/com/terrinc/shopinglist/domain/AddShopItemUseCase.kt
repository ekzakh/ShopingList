package com.terrinc.shopinglist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository,
) {

    suspend fun addItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}
