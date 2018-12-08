package com.gokhanaliccii.httpclient.annotation.url


@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Query(val value: String, val encoded: Boolean = false)