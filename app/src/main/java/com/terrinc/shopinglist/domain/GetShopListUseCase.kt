package com.terrinc.shopinglist.domain

import androidx.lifecycle.LiveData

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopItems()
    }
}
