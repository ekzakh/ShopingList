package com.terrinc.shopinglist.presentation.shoplist

import androidx.lifecycle.ViewModel
import com.terrinc.shopinglist.data.ShopListRepositoryImp
import com.terrinc.shopinglist.domain.DeleteShopItemUseCase
import com.terrinc.shopinglist.domain.EditShopItemUseCase
import com.terrinc.shopinglist.domain.GetShopListUseCase
import com.terrinc.shopinglist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImp

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteItem(shopItem)
    }

    fun changeEnableStateShopItem(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editItem(newItem)
    }
}
