package com.uos.mybilibili.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author xxc
 * @date 2020年9月30日17:32:58
 * 描述:Fragment 生命周期
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
