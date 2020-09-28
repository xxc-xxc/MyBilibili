package com.uos.mybilibili.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author xxc
 * @date 2020年9月28日14:50:55
 * 描述:Activity 生命周期
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
