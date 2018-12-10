package com.gokhanaliccii.httpclient.annotation.method

import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TYPE(val value: KClass<*>, val isArray: Boolean = false)

