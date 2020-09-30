package com.uos.mybilibili.di.component;

import android.app.Activity;

import com.uos.mybilibili.di.module.FragmentModule;
import com.uos.mybilibili.di.scope.FragmentScope;

import dagger.Component;

/**
 * Create By xxc
 * Date: 2020/9/30 17:31
 * Desc:
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent {

    Activity getActivity();

}
