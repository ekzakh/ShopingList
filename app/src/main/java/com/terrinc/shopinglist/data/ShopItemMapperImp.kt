package com.terrinc.shopinglist.data

import com.terrinc.shopinglist.domain.ShopItem

class ShopItemMapperImp: ShopItemMapper {

    override fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    override fun mapDbModelToEntity(shopItemDb: ShopItemDbModel) = ShopItem(
        id = shopItemDb.id,
        name = shopItemDb.name,
        count = shopItemDb.count,
        enabled = shopItemDb.enabled
    )

    override fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

}
