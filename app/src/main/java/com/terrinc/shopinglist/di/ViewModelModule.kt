package com.terrinc.shopinglist.di

import androidx.lifecycle.ViewModel
import com.terrinc.shopinglist.presentation.shopitem.ShopItemViewModel
import com.terrinc.shopinglist.presentation.shoplist.ShopListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ShopListViewModel::class)
    fun bindShopListViewModel(impl: ShopListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun binShopItemViewModel(impl: ShopItemViewModel): ViewModel
}
