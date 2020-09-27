package com.uos.mybilibili.mvp.contract;

import com.uos.mybilibili.base.BaseContract;
import com.uos.mybilibili.bean.Splash;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Create By xxc
 * Date: 2020/9/27 15:38
 * Desc: 欢迎页（闪屏页）契约类 封装MVP三者关系
 */
public interface SplashContract {

    interface Model {
        Flowable<Splash> getSplash();
    }

    interface View extends BaseContract.BaseView {
        void showCountdown(int count);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void setCountdown();
    }

}
