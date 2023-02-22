package com.terrinc.shopinglist.di

import android.app.Application
import com.terrinc.shopinglist.data.ShopItemProvider
import com.terrinc.shopinglist.presentation.shopitem.ShopItemFragment
import com.terrinc.shopinglist.presentation.shoplist.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(fragment: ShopItemFragment)
    fun inject(activity: MainActivity)
    fun inject(shopItemProvider: ShopItemProvider)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
