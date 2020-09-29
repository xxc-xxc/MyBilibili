package com.uos.mybilibili.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * APP注解，用以区分不同的retrofit，因为retrofit网络请求的url不一样
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface AppUrl {

}