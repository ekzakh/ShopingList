package com.terrinc.shopinglist

import android.app.Application
import com.terrinc.shopinglist.di.DaggerAppComponent

class ShopListApp: Application() {
    val component by lazy { DaggerAppComponent.create() }
}
