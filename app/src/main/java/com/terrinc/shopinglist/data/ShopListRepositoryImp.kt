package com.terrinc.shopinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.terrinc.shopinglist.di.AppScope
import com.terrinc.shopinglist.domain.ShopItem
import com.terrinc.shopinglist.domain.ShopListRepository
import javax.inject.Inject

@AppScope
class ShopListRepositoryImp @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopItemMapper,
) : ShopListRepository {

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
