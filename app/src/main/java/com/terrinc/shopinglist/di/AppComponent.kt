package com.terrinc.shopinglist.di

import com.terrinc.shopinglist.presentation.shopitem.ShopItemActivity
import com.terrinc.shopinglist.presentation.shoplist.MainActivity
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: ShopItemActivity)
    fun inject(activity: MainActivity)
}
