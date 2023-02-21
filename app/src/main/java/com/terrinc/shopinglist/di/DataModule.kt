package com.terrinc.shopinglist.di

import android.app.Application
import com.terrinc.shopinglist.data.AppDatabase
import com.terrinc.shopinglist.data.ShopItemMapper
import com.terrinc.shopinglist.data.ShopItemMapperImp
import com.terrinc.shopinglist.data.ShopListDao
import com.terrinc.shopinglist.data.ShopListRepositoryImp
import com.terrinc.shopinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(imp: ShopListRepositoryImp): ShopListRepository

    @Binds
    fun bindMapper(imp: ShopItemMapperImp): ShopItemMapper

    companion object {

        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopItemDao()
        }
    }
}
