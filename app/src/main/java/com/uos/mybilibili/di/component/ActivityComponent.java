package com.uos.mybilibili.di.component;

import android.app.Activity;

import com.uos.mybilibili.activity.SplashActivity;
import com.uos.mybilibili.di.module.ActivityModule;
import com.uos.mybilibili.di.scope.ActivityScope;

import dagger.Component;

/**
 * Create By xxc
 * Date: 2020/9/28 14:23
 * Desc:
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity getActivity();

    void inject(SplashActivity splashActivity);
}
