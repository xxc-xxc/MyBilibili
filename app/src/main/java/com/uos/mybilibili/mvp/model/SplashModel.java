package com.uos.mybilibili.mvp.model;

import com.uos.mybilibili.bean.Splash;
import com.uos.mybilibili.mvp.contract.SplashContract;
import com.uos.mybilibili.network.helper.RetrofitHelper;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Create By xxc
 * Date: 2020/9/27 17:00
 * Desc:
 */
public class SplashModel implements SplashContract.Model {
    @Override
    public Flowable<Splash> getSplash() {
        return RetrofitHelper.getInstance().getAppService().getSplash();
    }
}
