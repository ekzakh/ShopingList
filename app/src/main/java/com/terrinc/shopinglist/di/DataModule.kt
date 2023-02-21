package com.terrinc.shopinglist.di

import com.terrinc.shopinglist.data.ShopListRepositoryImp
import com.terrinc.shopinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindRepository(imp: ShopListRepositoryImp): ShopListRepository
}
