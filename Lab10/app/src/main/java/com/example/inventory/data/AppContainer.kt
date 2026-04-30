
package com.example.inventory.data

import android.content.Context


interface AppContainer {
    val itemsRepository: ItemsRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: ItemsRepository by lazy {
        // Phải truyền Database vào đây
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}
