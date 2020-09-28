package com.uos.mybilibili.di.component;

import android.content.Context;

import com.uos.mybilibili.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Create By xxc
 * Date: 2020/9/28 15:00
 * Desc:
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getContext();
}
