package com.uptech.repositorysample

import android.app.Application
import com.uptech.repositorysample.data.DaggerRepositoryComponent
import com.uptech.repositorysample.data.RepositoryComponent
import com.uptech.repositorysample.data.di.DaggerDataSourceComponent
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.main.di.core.CoreComponent
import com.uptech.repositorysample.main.di.core.DaggerCoreComponent

class App : Application() {
  lateinit var applicationComponent: ApplicationComponent
  lateinit var dataSourceComponent: DataSourceComponent
  lateinit var repositoryComponent: RepositoryComponent
  private lateinit var coreComponent: CoreComponent

  override fun onCreate() {
    super.onCreate()
    coreComponent = DaggerCoreComponent.builder()
      .build()
    dataSourceComponent = DaggerDataSourceComponent.builder()
      .context(applicationContext)
      .build()
    repositoryComponent = DaggerRepositoryComponent.builder()
      .dataSourceComponent(dataSourceComponent)
      .build()
    applicationComponent = DaggerApplicationComponent.builder()
      .coreComponent(coreComponent)
      .dataSourceComponent(dataSourceComponent)
      .repositoryComponent(repositoryComponent)
      .build()
  }
}