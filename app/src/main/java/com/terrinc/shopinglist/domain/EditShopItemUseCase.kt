package com.terrinc.shopinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}
