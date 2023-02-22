package com.terrinc.shopinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.terrinc.shopinglist.ShopListApp
import javax.inject.Inject

class ShopItemProvider : ContentProvider() {

    @Inject
    lateinit var shopItemsDao: ShopListDao
    private val component by lazy {
        (context as ShopListApp).component
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.terrinc.shopinglist", "shop_items", QUERY_CODE)
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

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object {
        private const val QUERY_CODE = 100
        const val ID = "id"
        const val NAME = "name"
        const val ENABLED = "enabled"
        const val COUNT = "count"
    }
}
