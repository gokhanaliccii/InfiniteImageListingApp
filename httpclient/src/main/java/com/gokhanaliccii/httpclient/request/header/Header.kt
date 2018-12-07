package com.gokhanaliccii.httpclient.request.header


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Header(val value: Array<String>)