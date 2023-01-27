package com.terrinc.shopinglist.presentation.shoplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terrinc.shopinglist.domain.DeleteShopItemUseCase
import com.terrinc.shopinglist.domain.EditShopItemUseCase
import com.terrinc.shopinglist.domain.GetShopListUseCase
import com.terrinc.shopinglist.domain.ShopItem
import com.terrinc.shopinglist.domain.ShopListRepository
import kotlinx.coroutines.launch

class ShopListViewModel(repository: ShopListRepository) : ViewModel() {

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteItem(shopItem)
        }
    }

    fun changeEnableStateShopItem(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        viewModelScope.launch {
            editShopItemUseCase.editItem(newItem)
        }
    }
}
