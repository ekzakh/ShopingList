package com.terrinc.shopinglist.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.terrinc.shopinglist.domain.ShopListRepository
import com.terrinc.shopinglist.presentation.shopitem.ShopItemViewModel
import com.terrinc.shopinglist.presentation.shoplist.ShopListViewModel

class ShopListViewModelFactory(private val repository: ShopListRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ShopListViewModel::class.java -> ShopListViewModel(repository) as T
        ShopItemViewModel::class.java -> ShopItemViewModel(repository) as T
        else -> throw IllegalArgumentException("Unknown view model $modelClass")
    }
}
