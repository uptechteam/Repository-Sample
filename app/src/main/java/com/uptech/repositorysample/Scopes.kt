package com.uptech.repositorysample

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.BINARY

@Scope
@Retention(BINARY)
annotation class ApplicationScope

@Scope
@Retention(BINARY)
annotation class AuthenticatedScope

@Scope
@Retention(BINARY)
annotation class ActivityScope

@Scope
@Retention(BINARY)
annotation class FragmentScope

