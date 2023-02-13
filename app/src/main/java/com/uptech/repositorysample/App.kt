package com.uptech.repositorysample

import android.app.Application
import com.uptech.repositorysample.data.di.DaggerDataSourceComponent
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.main.di.core.CoreComponent
import com.uptech.repositorysample.main.di.core.DaggerCoreComponent

class App : Application() {
  lateinit var applicationComponent: ApplicationComponent
  lateinit var dataSourceComponent: DataSourceComponent
  private lateinit var coreComponent: CoreComponent

  override fun onCreate() {
    super.onCreate()
    coreComponent = DaggerCoreComponent.builder()
      .build()
    dataSourceComponent = DaggerDataSourceComponent.builder()
      .context(applicationContext)
      .build()
    applicationComponent = DaggerApplicationComponent.builder()
      .coreComponent(coreComponent)
      .dataSourceComponent(dataSourceComponent)
      .build()
  }
}