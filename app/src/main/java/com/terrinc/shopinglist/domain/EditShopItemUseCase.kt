package com.terrinc.shopinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository,
) {

    suspend fun editItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}
