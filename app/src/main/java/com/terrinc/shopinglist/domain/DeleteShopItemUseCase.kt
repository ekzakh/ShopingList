package com.terrinc.shopinglist.domain

import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository,
) {

    suspend fun deleteItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}
