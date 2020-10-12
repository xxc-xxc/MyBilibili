package com.uos.mybilibili.di.component;

import android.content.Context;

import com.uos.mybilibili.di.module.ApiModule;
import com.uos.mybilibili.di.module.AppModule;
import com.uos.mybilibili.network.api.AppService;
import com.uos.mybilibili.network.api.LiveService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Create By xxc
 * Date: 2020/9/28 15:00
 * Desc:
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    Context getContext();

    AppService getAppService();

    LiveService getLiveService();
}
