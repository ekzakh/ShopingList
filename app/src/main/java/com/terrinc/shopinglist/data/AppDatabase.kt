package com.terrinc.shopinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopItemDao(): ShopListDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val NAME = "shop_item.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val bd = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    NAME
                ).build()
                INSTANCE = bd
                return bd
            }
        }
    }
}
