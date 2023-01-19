package com.uptech.repositorysample.data

sealed class Cache<out T: Any> {
  data class Data<out T: Any>(val value: T) : Cache<T>()
  object Expired : Cache<Nothing>()
}