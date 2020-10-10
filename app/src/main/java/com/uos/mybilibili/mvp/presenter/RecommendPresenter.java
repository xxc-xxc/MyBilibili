package com.uos.mybilibili.mvp.presenter;

import android.content.Context;

import com.uos.mybilibili.base.BaseListSubscriber;
import com.uos.mybilibili.base.BaseSubscriber;
import com.uos.mybilibili.base.RxPresenter;
import com.uos.mybilibili.bean.recommend.Recommend;
import com.uos.mybilibili.mvp.contract.RecommendContract;
import com.uos.mybilibili.mvp.model.RecommendModel;
import com.uos.mybilibili.network.response.HttpResponse;
import com.uos.mybilibili.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Create By xxc
 * Date: 2020/10/10 10:19
 * Desc:
 */
public class RecommendPresenter extends RxPresenter<RecommendContract.View> implements RecommendContract.Presenter<RecommendContract.View> {

    private Context mContext;

    @Inject
    RecommendModel mModel;

    @Inject
    public RecommendPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void getRecommend() {
        BaseListSubscriber<Recommend> subscriber = mModel.getRecommend()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseListSubscriber<Recommend>(mView) {
                    @Override
                    public void onSuccess(List<Recommend> recommends) {
                        // 成功回调
                        mView.showRecommend(recommends);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        // 失败回调
                        super.onFailure(code, message);
                    }
                });
        addSubscribe(subscriber);
    }
}
