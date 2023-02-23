package com.terrinc.shopinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.terrinc.shopinglist.ShopListApp
import com.terrinc.shopinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemProvider : ContentProvider() {

    @Inject
    lateinit var shopItemsDao: ShopListDao
    @Inject
    lateinit var mapper: ShopItemMapper

    private val scope = CoroutineScope(Dispatchers.IO)

    private val component by lazy {
        (context as ShopListApp).component
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, PATH, QUERY_CODE)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            QUERY_CODE -> {
                shopItemsDao.getShopItemsCursor()
            }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(
        uri: Uri,
        contentValues: ContentValues?,
    ): Uri? {
        when (uriMatcher.match(uri)) {
            QUERY_CODE -> {
                if (contentValues == null) return null
                val id = contentValues.getAsInteger(ID)
                val name = contentValues.getAsString(NAME)
                val count = contentValues.getAsInteger(COUNT)
                val enabled = contentValues.getAsBoolean(ENABLED)

                val shopItem = ShopItem(id = id, name = name, count = count, enabled = enabled)
                scope.launch {
                    shopItemsDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
                }
            }
        }
        return null
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        when(uriMatcher.match(uri)) {
            QUERY_CODE -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopItemsDao.deleteShopItemSync(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        when(uriMatcher.match(uri)) {
            QUERY_CODE -> {
                if (contentValues == null) return 0
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                val name = contentValues.getAsString(NAME)
                val count = contentValues.getAsInteger(COUNT)
                scope.launch {
                    val shopItemOld = shopItemsDao.getShopItem(id)
                    val shopItemNew = shopItemOld.copy(name = name, count = count)
                    shopItemsDao.addShopItem(shopItemNew)
                }
            }
        }
        return 0
    }

    companion object {
        private const val QUERY_CODE = 100
        const val AUTHORITY = "com.terrinc.shopinglist"
        const val PATH = "shop_items"
        const val URI = "content://$AUTHORITY/$PATH"
        const val ID = "id"
        const val NAME = "name"
        const val ENABLED = "enabled"
        const val COUNT = "count"
    }
}
