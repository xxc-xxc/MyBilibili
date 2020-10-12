package com.uos.mybilibili.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author xxc
 * @date 创建时间：2020年10月12日16:07:18
 * 描述:
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface LiveUrl {

}