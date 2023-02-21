package com.terrinc.shopinglist.di

import androidx.lifecycle.ViewModel
import com.terrinc.shopinglist.presentation.shopitem.ShopItemViewModel
import com.terrinc.shopinglist.presentation.shoplist.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun binShopItemViewModel(impl: ShopItemViewModel): ViewModel
}
