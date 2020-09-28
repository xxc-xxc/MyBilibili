package com.uos.mybilibili.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Create By xxc
 * Date: 2020/9/28 14:21
 * Desc:
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }
}
