package com.terrinc.shopinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository,
) {

    fun getList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopItems()
    }
}
