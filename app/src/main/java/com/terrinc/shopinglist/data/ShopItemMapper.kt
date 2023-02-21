package com.terrinc.shopinglist.data

import com.terrinc.shopinglist.domain.ShopItem

interface ShopItemMapper {
    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel

    fun mapDbModelToEntity(shopItemDb: ShopItemDbModel): ShopItem

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>): List<ShopItem>
}
