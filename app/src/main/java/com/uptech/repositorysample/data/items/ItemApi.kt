package com.uptech.repositorysample.data.items

import com.uptech.repositorysample.R
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.entity.Item
import kotlinx.coroutines.flow.first

class ItemApi(private val userManager: UserManager) {
  private val mockedData: MutableList<Item> = mutableListOf(
    Item("dagon_razon", "Dagon's razor", 1000, 4, R.drawable.razor),
    Item("elder_scroll", "Elden scroll", 4000, 3, R.drawable.elden_scroll),
    Item("legion_armor", "Legion armor", 200, 14, R.drawable.legion_armor),
    Item("daedra_heart", "Daedra heart", 1700, 1, R.drawable.daedra_heart),
    Item("dragonpriest_mask", "Dragon priest mask", 1200, 2, R.drawable.dragonpriest_mask),
  )

  suspend fun getItems(): List<Item> =
    if (userManager.userToken.first() === null) {
      throw IllegalStateException("Unauthorized request")
    } else {
      mockedData
    }

  fun buy(item: Item) {
    mockedData[mockedData.indexOfFirst { it.id == item.id }] = item.copy(countOnStock = item.countOnStock - 1)
  }
}