package com.uos.mybilibili.mvp.presenter;

import android.content.Context;

import com.uos.mybilibili.base.BaseObjectSubscriber;
import com.uos.mybilibili.base.RxPresenter;
import com.uos.mybilibili.bean.live.LiveRecommend;
import com.uos.mybilibili.mvp.contract.LiveContract;
import com.uos.mybilibili.mvp.model.LiveModel;
import com.uos.mybilibili.utils.RxUtils;

import javax.inject.Inject;

/**
 * Create By xxc
 * Date: 2020/10/12 16:27
 * Desc:
 */
public class LivePresenter extends RxPresenter<LiveContract.View>
        implements LiveContract.Presenter<LiveContract.View> {

    private Context mContext;
    @Inject
    LiveModel mModel;

    @Inject
    public LivePresenter(Context context) {
        mContext = context;
    }

    @Override
    public void getLiveData() {
        BaseObjectSubscriber<LiveRecommend> subscriber = mModel.getLivePartition()
                .compose(RxUtils.handleResult())
                .flatMap(livePartition -> {
                    mView.showLivePartition(livePartition);
                    // 请求直播推荐
                    return mModel.getLiveRecommend();
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObjectSubscriber<LiveRecommend>(mView) {
                    @Override
                    public void onSuccess(LiveRecommend liveRecommend) {
                        mView.showLiveRecommend(liveRecommend);
                    }
                });
        addSubscribe(subscriber);
    }
}
