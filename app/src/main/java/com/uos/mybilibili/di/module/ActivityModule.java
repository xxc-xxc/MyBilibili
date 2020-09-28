package com.uos.mybilibili.di.module;

import android.app.Activity;

import com.uos.mybilibili.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Create By xxc
 * Date: 2020/9/28 14:46
 * Desc: Activity组件，提供Activity依赖注入
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }

}
