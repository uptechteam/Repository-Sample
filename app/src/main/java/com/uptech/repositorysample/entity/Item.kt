package com.uptech.repositorysample.entity

import androidx.annotation.DrawableRes

data class Item(
  val id: String,
  val name: String,
  val cost: Long,
  val countOnStock: Int,
  @DrawableRes val imageRes: Int
)