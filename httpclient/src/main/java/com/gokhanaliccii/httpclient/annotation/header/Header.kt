package com.gokhanaliccii.httpclient.annotation.header


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Header(val value: Array<String>)