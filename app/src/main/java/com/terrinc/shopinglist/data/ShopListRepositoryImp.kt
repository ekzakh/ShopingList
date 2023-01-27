package com.terrinc.shopinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.terrinc.shopinglist.domain.ShopItem
import com.terrinc.shopinglist.domain.ShopListRepository

class ShopListRepositoryImp(application: Application) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopItemDao()
    private val mapper = ShopItemMapperImp()

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        return mapper.mapDbModelToEntity(shopListDao.getShopItem(shopItemId))
    }

    override fun getShopItems(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopItems()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }
}
