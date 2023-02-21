package com.terrinc.shopinglist.presentation.shoplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terrinc.shopinglist.domain.DeleteShopItemUseCase
import com.terrinc.shopinglist.domain.EditShopItemUseCase
import com.terrinc.shopinglist.domain.GetShopListUseCase
import com.terrinc.shopinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopListViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
) : ViewModel() {

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
