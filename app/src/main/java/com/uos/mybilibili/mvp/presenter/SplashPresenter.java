package com.uos.mybilibili.mvp.presenter;

import android.content.Context;

import com.uos.mybilibili.base.RxPresenter;
import com.uos.mybilibili.mvp.contract.SplashContract;
import com.uos.mybilibili.mvp.model.SplashModel;
import com.uos.mybilibili.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Create By xxc
 * Date: 2020/9/27 17:02
 * Desc:
 */
public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View> {

    private Context mContext;
    private SplashContract.Model mModel;

    public SplashPresenter(Context context) {
        mContext = context;
        mModel = new SplashModel();
    }

    @Override
    public void setCountdown() {
        final Long count = 5L;
        Disposable subscribe = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> count - aLong)
                .take(count + 1)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(aLong -> mView.showCountdown(aLong.intValue()));
        addSubscribe(subscribe);
    }
}
